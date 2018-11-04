package be.kdg.processor.business.violation;

import be.kdg.processor.business.domain.camera.Camera;
import be.kdg.processor.business.domain.camera.ProcessedCameraMessage;
import be.kdg.processor.business.domain.fine.Fine;
import be.kdg.processor.business.domain.settings.Settings;
import be.kdg.processor.business.domain.vehicle.Vehicle;
import be.kdg.processor.business.domain.violation.Violation;
import be.kdg.processor.business.domain.violation.ViolationType;
import be.kdg.processor.business.service.SettingsService;
import be.kdg.processor.business.settings.SettingsListener;
import net.jodah.expiringmap.ExpiringMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class SpeedingViolation implements ViolationStrategy, SettingsListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpeedingViolation.class);

    @Autowired
    private ExpiringMap<String, List<ProcessedCameraMessage>> bufferedSpeedCameraMessages;

    @Autowired
    private SettingsService settingsService;

    @Override
    public Optional<Violation> detect(ProcessedCameraMessage processedCameraMessage) {

        Vehicle vehicle = processedCameraMessage.getVehicle();
        Camera camera = processedCameraMessage.getCamera();

        // check if we have a buffered message for the plate
        if (bufferedSpeedCameraMessages.containsKey(vehicle.getPlateId())) {

            // loop through buffered messages
            for (ProcessedCameraMessage previousMessage : bufferedSpeedCameraMessages.get(vehicle.getPlateId())) {

                Camera previousCamera = previousMessage.getCamera();

                // make sure the previous camera segment not null (would indicate emissionCam)
                if (previousCamera.getSegment() != null
                        && previousCamera.getSegment().getConnectedCameraId() == camera.getId()) {

                    // calculate speed
                    double speed = calculateSpeed(previousMessage, processedCameraMessage);

                    // check if speeding violation
                    if (speed > previousCamera.getSegment().getSpeedLimit()){

                        bufferedSpeedCameraMessages.remove(vehicle.getPlateId());

                        return Optional.of(new Violation(
                                ViolationType.SPEEDING,
                                speed,
                                previousCamera.getSegment().getSpeedLimit(),
                                vehicle.getEuroNumber(),
                                vehicle.getPlateId(),
                                processedCameraMessage.getTimeStamp(),
                                previousCamera.getId(),
                                previousCamera.getSegment().getConnectedCameraId()));
                    }
                }
            }

        } else {
            // if no previous messages detected, add the current one
            bufferedSpeedCameraMessages.put(vehicle.getPlateId(), new ArrayList<>());
            bufferedSpeedCameraMessages.get(vehicle.getPlateId()).add(processedCameraMessage);
        }
        return Optional.empty();
    }

    @Override
    public Fine calculateFine(Violation violation) {

        Settings settings = settingsService.loadSettings();

        double fineAmount = (violation.getSpeed() - violation.getSpeedLimit()) * settings.getSpeedFactor();

        return new Fine(fineAmount, false, null, violation);
    }

    @Override
    public void onSettingsChanged(Settings settings) {

        bufferedSpeedCameraMessages.setExpiration(settings.getSpeedingBufferTimeInMinutes(), TimeUnit.MINUTES);
        LOGGER.info("Updated: Buffered messages buffer settings with: {}", settings);
    }

    private double calculateSpeed(ProcessedCameraMessage previousMessage, ProcessedCameraMessage processedMessage){

        Camera previousCamera = previousMessage.getCamera();

        double ms = ChronoUnit.MICROS.between(previousMessage.getTimeStamp(), processedMessage.getTimeStamp());
        double seconds = ms / 1000000;

        // convert m/s -> km/h + return
        return (previousCamera.getSegment().getDistance()/seconds) * 3.6;
    }
}

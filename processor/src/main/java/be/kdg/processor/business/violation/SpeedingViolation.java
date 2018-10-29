package be.kdg.processor.business.violation;

import be.kdg.processor.business.domain.camera.Camera;
import be.kdg.processor.business.domain.camera.ProcessedCameraMessage;
import be.kdg.processor.business.domain.fine.Fine;
import be.kdg.processor.business.domain.fine.FineFactor;
import be.kdg.processor.business.domain.vehicle.Vehicle;
import be.kdg.processor.business.domain.violation.Violation;
import be.kdg.processor.business.domain.violation.ViolationType;
import be.kdg.processor.business.service.FineFactorService;
import org.apache.commons.collections4.map.PassiveExpiringMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SpeedingViolation implements ViolationStrategy {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpeedingViolation.class);

    @Autowired
    private PassiveExpiringMap<String, List<ProcessedCameraMessage>> bufferedSpeedCameraMessages;

    @Autowired
    private FineFactorService fineFactorService;

    @Override
    public Optional<Violation> detect(ProcessedCameraMessage processedCameraMessage) {

        Vehicle vehicle = processedCameraMessage.getVehicle();
        Camera camera = processedCameraMessage.getCamera();

        if (bufferedSpeedCameraMessages.containsKey(vehicle.getPlateId())) {

            for (ProcessedCameraMessage previousMessage : bufferedSpeedCameraMessages.get(vehicle.getPlateId())) {

                Camera previousCamera = previousMessage.getCamera();

                if (previousCamera.getSegment() != null
                        && previousCamera.getSegment().getConnectedCameraId() == camera.getId()) {

                    double ms = ChronoUnit.MILLIS.between(previousMessage.getTimeStamp(), processedCameraMessage.getTimeStamp());
                    LOGGER.warn("MS: "+ms);
                    double seconds = ms / 1000;
                    LOGGER.warn("SECONDS: "+seconds);
                    // convert m/s -> km/h
                    double speed = (previousCamera.getSegment().getDistance()/seconds) * 3.6;
                    LOGGER.warn("ALLOWED: "+ previousCamera.getSegment().getSpeedLimit());
                    LOGGER.warn("SPEED: "+speed);

                    if (speed > previousCamera.getSegment().getSpeedLimit()){
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

            bufferedSpeedCameraMessages.put(vehicle.getPlateId(), new ArrayList<>());
            bufferedSpeedCameraMessages.get(vehicle.getPlateId()).add(processedCameraMessage);
        }
        return Optional.empty();
    }

    @Override
    public Fine calculateFine(Violation violation) {

        FineFactor fineFactor = fineFactorService.loadFineFactor();

        double fineAmount = (violation.getSpeed() - violation.getSpeedLimit()) * fineFactor.getSpeedFactor();

        return new Fine(fineAmount, false, null, violation);
    }
}

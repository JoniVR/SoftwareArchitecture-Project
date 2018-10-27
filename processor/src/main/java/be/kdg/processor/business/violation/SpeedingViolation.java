package be.kdg.processor.business.violation;

import be.kdg.processor.domain.camera.Camera;
import be.kdg.processor.domain.camera.ProcessedCameraMessage;
import be.kdg.processor.domain.fine.Fine;
import be.kdg.processor.domain.vehicle.Vehicle;
import org.apache.commons.collections4.map.PassiveExpiringMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class SpeedingViolation implements ViolationStrategy {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpeedingViolation.class);

    @Autowired
    private PassiveExpiringMap<String, List<ProcessedCameraMessage>> bufferedSpeedCameraMessages;

    @Override
    public boolean detect(ProcessedCameraMessage processedCameraMessage) {

        Vehicle vehicle = processedCameraMessage.getVehicle();
        Camera camera = processedCameraMessage.getCamera();

        if (bufferedSpeedCameraMessages.containsKey(vehicle.getPlateId())) {

            for (ProcessedCameraMessage previousMessage : bufferedSpeedCameraMessages.get(vehicle.getPlateId())) {

                Camera previousCamera = previousMessage.getCamera();

                if (previousCamera.getSegment() != null
                        && previousCamera.getSegment().getConnectedCameraId() == camera.getId()) {

                    //TODO: implement
                    long seconds = ChronoUnit.MICROS.between(previousMessage.getTimeStamp(), processedCameraMessage.getTimeStamp());
                    LOGGER.warn("MINUTES: "+seconds);
                    LOGGER.warn("T1: "+previousMessage.getTimeStamp()+ "T2: " + processedCameraMessage.getTimeStamp());
                    long speed = previousCamera.getSegment().getDistance()/seconds;
                    LOGGER.warn("SPEED: "+speed);
                    LOGGER.info("Detected: speed violation for {}", vehicle);
                }
            }

        } else {

            LOGGER.warn("ADDED TO THE LIST: "+vehicle.getPlateId());
            bufferedSpeedCameraMessages.put(vehicle.getPlateId(), new ArrayList<>());
            bufferedSpeedCameraMessages.get(vehicle.getPlateId()).add(processedCameraMessage);
        }
        return false;
    }

    @Override
    public Fine calculateFine(ProcessedCameraMessage processedCameraMessage) {

        return null;
    }
}

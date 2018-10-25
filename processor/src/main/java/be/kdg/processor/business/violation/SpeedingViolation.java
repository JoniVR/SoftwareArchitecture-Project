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

        if(camera.getSegment() == null) return false;

        if (bufferedSpeedCameraMessages.containsKey(vehicle.getPlateId())) {

            for (ProcessedCameraMessage previousMessage : bufferedSpeedCameraMessages.get(vehicle.getPlateId())) {

                if (previousMessage.getCamera().getId() == camera.getSegment().getConnectedCameraId()
                        && camera.getSegment().equals(previousMessage.getCamera().getSegment())) {

                    //TODO: implement
                    long minutes = ChronoUnit.MINUTES.between(previousMessage.getTimeStamp(), processedCameraMessage.getTimeStamp());
                    LOGGER.warn("MINUTES: "+minutes);
                    LOGGER.warn("T1: "+previousMessage.getTimeStamp()+ "T2: "+processedCameraMessage.getTimeStamp());
                    long speed = camera.getSegment().getDistance()/minutes;
                    LOGGER.warn("SPEED: "+speed);
                    LOGGER.info("Speed violation detected for {}", vehicle);
                }
            }

        } else {
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

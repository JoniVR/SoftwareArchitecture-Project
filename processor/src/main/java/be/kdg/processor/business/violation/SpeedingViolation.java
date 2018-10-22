package be.kdg.processor.business.violation;

import be.kdg.processor.domain.camera.Camera;
import be.kdg.processor.domain.fine.Fine;
import be.kdg.processor.domain.vehicle.Vehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpeedingViolation implements ViolationStrategy {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpeedingViolation.class);

    @Override
    public boolean detect(Camera camera, Vehicle vehicle) {

        return false;
    }

    @Override
    public Fine calculateFine(Camera camera, Vehicle vehicle) {

        return null;
    }
}

package be.kdg.processor.violation;

import be.kdg.processor.model.camera.Camera;
import be.kdg.processor.model.fine.Fine;
import be.kdg.processor.model.vehicle.Vehicle;
import jdk.jshell.spi.ExecutionControl;
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

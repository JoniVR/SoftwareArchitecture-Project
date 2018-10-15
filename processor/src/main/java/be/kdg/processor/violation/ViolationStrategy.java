package be.kdg.processor.violation;

import be.kdg.processor.model.camera.Camera;
import be.kdg.processor.model.fine.Fine;
import be.kdg.processor.model.vehicle.Vehicle;

public interface ViolationStrategy {

    boolean detect(Camera camera, Vehicle vehicle);

    Fine calculateFine(Camera camera, Vehicle vehicle);
}

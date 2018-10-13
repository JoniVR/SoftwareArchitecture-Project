package be.kdg.processor.violation;

import be.kdg.processor.model.camera.Camera;
import be.kdg.processor.model.vehicle.Vehicle;

public interface ViolationStrategy {

    void detect(Camera camera, Vehicle vehicle);
}

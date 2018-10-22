package be.kdg.processor.business.violation;

import be.kdg.processor.domain.camera.Camera;
import be.kdg.processor.domain.fine.Fine;
import be.kdg.processor.domain.vehicle.Vehicle;

public interface ViolationStrategy {

    boolean detect(Camera camera, Vehicle vehicle);

    Fine calculateFine(Camera camera, Vehicle vehicle);
}

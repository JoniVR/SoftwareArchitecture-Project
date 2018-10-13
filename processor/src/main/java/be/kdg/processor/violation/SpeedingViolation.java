package be.kdg.processor.violation;

import be.kdg.processor.model.camera.CameraMessage;

public class SpeedingViolation implements ViolationStrategy {

    @Override
    public void detect() {

    }

    @Override
    public void onCameraMessageReceived(CameraMessage cameraMessage) {

        System.out.println("Speeding-violation");
    }
}

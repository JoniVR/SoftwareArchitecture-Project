package be.kdg.processor.violation;

import be.kdg.processor.model.camera.CameraMessage;

public class EmissionViolation implements ViolationStrategy {

    @Override
    public void detect() {


    }

    @Override
    public void onCameraMessageReceived(CameraMessage cameraMessage) {

        System.out.println("emission-violation");
    }
}

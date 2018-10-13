package be.kdg.processor.processing;

import be.kdg.processor.model.camera.CameraMessage;

public interface ProcessorListener {

    void onCameraMessageReceived(CameraMessage cameraMessage);
}

package be.kdg.processor.model.fine;

import be.kdg.processor.model.camera.CameraMessage;

public interface FineStrategy {

    void calculateValue(CameraMessage cameraMessage);
}

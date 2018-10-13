package be.kdg.processor.violation;

import be.kdg.processor.model.camera.CameraMessage;

import java.io.IOException;

public interface ViolationStrategy {

    void detect(CameraMessage cameraMessage) throws IOException;
}

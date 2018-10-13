package be.kdg.processor.violation;

import be.kdg.processor.model.camera.CameraMessage;
import be.kdg.processor.service.ProxyService;
import org.springframework.beans.factory.annotation.Autowired;

public class SpeedingViolation implements ViolationStrategy {

    @Autowired
    private ProxyService proxyService;

    @Override
    public void detect(CameraMessage cameraMessage) {

    }
}

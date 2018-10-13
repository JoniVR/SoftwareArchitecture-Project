package be.kdg.processor.violation;

import be.kdg.processor.fine.EmissionFine;
import be.kdg.processor.mapping.JSONMapperService;
import be.kdg.processor.model.camera.CameraMessage;
import be.kdg.processor.service.ProxyService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * Detects if an emission violation has occured and calls the responsible class to handle this.
 */
public class EmissionViolation implements ViolationStrategy {

    @Autowired
    private ProxyService proxyService;

    @Autowired
    private EmissionFine emissionFine;

    @Autowired
    private JSONMapperService jsonMapperService;

    @Override
    public void detect(CameraMessage cameraMessage) throws IOException {

        int cameraId = cameraMessage.getId();

        proxyService.cameraServiceProxy().get(cameraId);
    }
}

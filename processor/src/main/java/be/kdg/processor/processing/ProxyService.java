package be.kdg.processor.processing;

import be.kdg.sa.services.CameraServiceProxy;
import be.kdg.sa.services.LicensePlateServiceProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class ProxyService {

    @Bean
    public CameraServiceProxy cameraServiceProxy() { return new CameraServiceProxy(); }

    @Bean
    public LicensePlateServiceProxy licensePlateServiceProxy() { return new LicensePlateServiceProxy(); }
}

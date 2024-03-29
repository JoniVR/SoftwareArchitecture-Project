package be.kdg.processor.business.service;

import be.kdg.sa.services.CameraServiceProxy;
import be.kdg.sa.services.LicensePlateServiceProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    public ProxyService proxyServiceAdapter() { return new ProxyService(); }

    @Bean
    public CameraServiceProxy cameraServiceProxy() { return new CameraServiceProxy(); }

    @Bean
    public LicensePlateServiceProxy licensePlateServiceProxy() { return new LicensePlateServiceProxy(); }
}

package be.kdg.processor.business.violation;

import be.kdg.processor.business.domain.camera.ProcessedCameraMessage;
import org.apache.commons.collections4.map.PassiveExpiringMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
public class ViolationConfig {

    @Bean
    public EmissionViolation emissionViolation() { return new EmissionViolation(); }

    @Bean
    public SpeedingViolation speedingViolation() { return new SpeedingViolation(); }

    @Bean
    public PassiveExpiringMap<String, List<ProcessedCameraMessage>> speedCameraMessages() { return new PassiveExpiringMap<>(30, TimeUnit.MINUTES); }
}

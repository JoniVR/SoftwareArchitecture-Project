package be.kdg.processor.model.violation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ViolationConfig {

    @Bean
    public EmissionViolation emissionViolation() { return new EmissionViolation(); }

    @Bean
    public SpeedingViolation speedingViolation() { return new SpeedingViolation(); }
}

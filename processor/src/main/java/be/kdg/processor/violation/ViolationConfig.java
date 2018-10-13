package be.kdg.processor.violation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class ViolationConfig {

    @Bean
    public EmissionViolation emissionViolation() { return new EmissionViolation(); }

    @Bean
    public SpeedingViolation speedingViolation() { return new SpeedingViolation(); }

    @Bean
    public HashMap<String, ViolationStrategy> violationStrategies() { return new HashMap<>(); }
}

package be.kdg.processor.fine;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class FineConfig {

    @Bean
    public EmissionFineStrategy emissionFineStrategy() { return new EmissionFineStrategy(); }

    @Bean
    public SpeedingFineStrategy speedingFineStrategy() { return new SpeedingFineStrategy(); }
}

package be.kdg.processor.fine;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FineConfig {

    @Bean
    public EmissionFine emissionFine() { return new EmissionFine(); }

    @Bean
    public SpeedingFine speedingFine() { return new SpeedingFine(); }
}

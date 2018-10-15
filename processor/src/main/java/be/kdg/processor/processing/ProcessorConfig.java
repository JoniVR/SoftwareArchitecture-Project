package be.kdg.processor.processing;

import be.kdg.processor.violation.ViolationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Configuration
public class ProcessorConfig {

    @Bean
    public Processor processor() { return new Processor(); }

    @Bean
    public ProcessorMessageHandler processorMessageHandler() { return new ProcessorMessageHandler(); }

    @Bean
    public ArrayList<ViolationStrategy> listeners() { return new ArrayList<>(); }
}
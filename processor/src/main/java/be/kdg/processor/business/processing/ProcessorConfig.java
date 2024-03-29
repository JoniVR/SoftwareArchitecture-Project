package be.kdg.processor.business.processing;

import be.kdg.processor.business.violation.ViolationStrategy;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.ArrayList;

@Configuration
@EnableRabbit
@EnableScheduling
public class ProcessorConfig {

    @Bean
    public Processor processor() { return new Processor(); }

    @Bean
    public ProcessorMessageHandler processorMessageHandler() { return new ProcessorMessageHandler(); }

    @Bean
    public ArrayList<ViolationStrategy> listeners() { return new ArrayList<>(); }
}
package be.kdg.processor.business.processing;

import be.kdg.processor.business.violation.ViolationStrategy;
import be.kdg.processor.domain.FailedQueueMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Configuration
@EnableScheduling
public class ProcessorConfig {

    @Bean
    public Processor processor() { return new Processor(); }

    @Bean
    public ProcessorMessageHandler processorMessageHandler() { return new ProcessorMessageHandler(); }

    @Bean
    public FailedMessageProcessor failedMessageProcessor() { return new FailedMessageProcessor(); }

    @Bean
    public ArrayList<ViolationStrategy> listeners() { return new ArrayList<>(); }

    @Bean
    public CopyOnWriteArrayList<FailedQueueMessage> failedQueueMessages() { return new CopyOnWriteArrayList<>(); }
}
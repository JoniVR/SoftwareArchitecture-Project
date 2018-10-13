package be.kdg.processor.processing;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Configuration
public class ProcessorConfig {

    @Bean
    public MessageHandler messageHandler() { return new MessageHandler(); }

    @Bean
    public ArrayList<ProcessorListener> listeners() { return new ArrayList<>(); }

}

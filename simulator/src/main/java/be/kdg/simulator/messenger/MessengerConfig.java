package be.kdg.simulator.messenger;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Used for configuring beans.
 */
@Configuration
public class MessengerConfig {

    @Bean
    @ConditionalOnProperty(name = "messenger.type", havingValue = "queue")
    public Messenger queueMessenger() { return new QueueMessenger(); }

    @Bean
    @ConditionalOnProperty(name = "messenger.type", havingValue = "cmd")
    public Messenger commandLineMessenger() { return new CommandLineMessenger(); }

}

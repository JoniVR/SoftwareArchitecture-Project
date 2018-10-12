package be.kdg.simulator.messenger;

import be.kdg.simulator.mapping.XMLMapperService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
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

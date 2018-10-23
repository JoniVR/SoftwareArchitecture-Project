package be.kdg.simulator.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Used for RabbitMQ configuration settings.
 */
@Configuration
public class RabbitConfig {

    public static final String EXCHANGE_NAME = "appExchange";
    public static final String ROUTING_KEY = "messages.key";
    private static final String MESSAGE_QUEUE = "messageQueue";

    @Bean
    public Queue queue() { return new Queue(MESSAGE_QUEUE); }

    @Bean
    TopicExchange exchange() { return new TopicExchange(EXCHANGE_NAME); }
}

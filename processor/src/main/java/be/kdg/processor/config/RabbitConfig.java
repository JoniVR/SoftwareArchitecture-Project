package be.kdg.processor.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Used for RebbitMQ configuration settings.
 */
@Configuration
public class RabbitConfig {

    public static final String QUEUE_SPECIFIC_NAME = "messageQueue";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_SPECIFIC_NAME);
    }
}

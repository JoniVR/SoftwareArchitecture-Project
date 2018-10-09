package be.kdg.simulator.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Used for RebbitMQ configuration settings.
 */
@Configuration
public class RabbitConfig {

    public static final String EXCHANGE_NAME = "appExchange";
    public static final String QUEUE_SPECIFIC_NAME = "messageQueue";
    public static final String ROUTING_KEY = "messages.key";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_SPECIFIC_NAME);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }
}

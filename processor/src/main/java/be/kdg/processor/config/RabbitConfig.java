package be.kdg.processor.config;

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
    public static final String MESSAGE_QUEUE = "messageQueue";
    public static final String ERROR_QUEUE = "errorQueue";
    public static final String ROUTING_MESSAGE_KEY = "messages.key";
    public static final String ROUTING_ERROR_KEY = "errors.key";

    @Bean
    public Queue messageQueue() { return new Queue(MESSAGE_QUEUE); }

    @Bean
    public Queue errorQueue() { return new Queue(ERROR_QUEUE); }

    @Bean
    public TopicExchange exchange() { return new TopicExchange(EXCHANGE_NAME); }

    @Bean
    public Binding declareBindingMessage() {
        return BindingBuilder.bind(messageQueue()).to(exchange()).with(ROUTING_MESSAGE_KEY);
    }

    @Bean
    public Binding declareBindingError() {
        return BindingBuilder.bind(errorQueue()).to(exchange()).with(ROUTING_ERROR_KEY);
    }
}

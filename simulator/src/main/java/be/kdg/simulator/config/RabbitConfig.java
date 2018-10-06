package be.kdg.simulator.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@EnableRabbit
@Configuration
public class RabbitConfig { //implements RabbitListenerConfigurer{

    public static final String EXCHANGE_NAME = "appExchange";
    public static final String QUEUE_SPECIFIC_NAME = "messageQueue";
    public static final String ROUTING_KEY = "messages.key";

    @Bean
    public Queue appQueueSpecific() {
        return new Queue(QUEUE_SPECIFIC_NAME);
    }
}

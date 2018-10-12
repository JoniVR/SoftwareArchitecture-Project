package be.kdg.processor.processing;

import be.kdg.processor.config.RabbitConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * Responsible for handling messages from the RabbitMq queue.
 */
@Service
public class Processor {

    private static final Logger LOGGER = LoggerFactory.getLogger(Processor.class);

    @RabbitListener(queues = RabbitConfig.QUEUE_SPECIFIC_NAME)
    public void receiveMessage(final String cameraMessage) {
        LOGGER.info("Received message as specific class: {}", cameraMessage);
    }
}

package be.kdg.simulator.messengers;

import be.kdg.simulator.config.RabbitConfig;
import be.kdg.simulator.models.CameraMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "messenger.type", havingValue = "queue")
public class QueueMessenger implements Messenger {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueueMessenger.class);

    private final RabbitTemplate rabbitTemplate;
    private final MessageOriginHandler messageOriginHandler;

    @Autowired
    public QueueMessenger(RabbitTemplate rabbitTemplate, MessageOriginHandler messageOriginHandler) {
        this.rabbitTemplate = rabbitTemplate;
        this.messageOriginHandler = messageOriginHandler;
    }

    @Override
    public void sendMessage() {

        CameraMessage cameraMessage = messageOriginHandler.relayMessage();

        LOGGER.info("Placing message on queue.");
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.ROUTING_KEY, cameraMessage);
    }
}

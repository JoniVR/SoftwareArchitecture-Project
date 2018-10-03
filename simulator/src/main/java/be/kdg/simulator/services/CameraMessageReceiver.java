package be.kdg.simulator.services;

import be.kdg.simulator.config.RabbitConfig;
import be.kdg.simulator.models.CameraMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class CameraMessageReceiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(CameraMessageReceiver.class);

    @RabbitListener(queues = RabbitConfig.QUEUE_SPECIFIC_NAME)
    public void receiveMessage(final CameraMessage cameraMessage) {
        LOGGER.info("Received message as specific class: {}", cameraMessage.toString());
    }
}

package be.kdg.simulator.messenger;

import be.kdg.simulator.config.RabbitConfig;
import be.kdg.simulator.model.CameraMessage;
import be.kdg.simulator.mapping.XMLMapperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueueMessenger implements Messenger {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueueMessenger.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private XMLMapperService xmlMapperService;

    @Override
    public void sendMessage(CameraMessage cameraMessage) {

        LOGGER.info("Placing message on queue.");
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.ROUTING_KEY, xmlMapperService.convertObjectToXml(cameraMessage));
    }
}

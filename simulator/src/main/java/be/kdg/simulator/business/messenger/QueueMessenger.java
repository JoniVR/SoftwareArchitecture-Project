package be.kdg.simulator.business.messenger;

import be.kdg.simulator.business.domain.CameraMessage;
import be.kdg.simulator.config.RabbitConfig;
import be.kdg.simulator.util.XMLMapperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class QueueMessenger implements Messenger {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueueMessenger.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private XMLMapperService xmlMapperService;

    @Override
    public void sendMessage(CameraMessage cameraMessage) throws IOException, AmqpException {

        LOGGER.info("Placing message on queue.");
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.ROUTING_KEY, xmlMapperService.convertObjectToXml(cameraMessage));
    }
}

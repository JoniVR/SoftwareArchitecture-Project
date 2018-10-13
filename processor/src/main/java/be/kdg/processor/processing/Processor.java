package be.kdg.processor.processing;

import be.kdg.processor.config.RabbitConfig;
import be.kdg.processor.mapping.XMLMapperService;
import be.kdg.processor.model.camera.CameraMessage;
import be.kdg.processor.service.ProxyService;
import be.kdg.processor.violation.ViolationStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Responsible for processing/handling of messages from the RabbitMq queue.
 * Receives the cameraMessage and notifies & passes it to the MessageHandler.
 *
 * @Author Joni Van Roost
 */

public class Processor {

    private static final Logger LOGGER = LoggerFactory.getLogger(Processor.class);

    @Autowired
    private XMLMapperService xmlMapperService;

    @Autowired
    private Collection<ViolationStrategy> listeners = new ArrayList<>();

    @Autowired
    private ProxyService proxyService;

    @RabbitListener(queues = RabbitConfig.QUEUE_SPECIFIC_NAME)
    public void receiveMessage(final String cameraMessageString) {

        LOGGER.info("Received message as specific class: {}", cameraMessageString);

        CameraMessage cameraMessage = xmlMapperService.convertXmlStringToCameraMessage(cameraMessageString);

        //TODO: handle exceptions
        listeners.forEach(listener -> {

            try {

                listener.detect(cameraMessage);

            } catch (IOException e) {

                LOGGER.warn("There's something wrong with the Services.", e);
            }
        });
    }
}

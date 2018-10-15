package be.kdg.processor.processing;

import be.kdg.processor.config.RabbitConfig;
import be.kdg.processor.exceptions.ObjectMappingException;
import be.kdg.processor.mapping.JSONMapperService;
import be.kdg.processor.mapping.XMLMapperService;
import be.kdg.processor.model.camera.Camera;
import be.kdg.processor.model.camera.CameraMessage;
import be.kdg.processor.model.vehicle.Vehicle;
import be.kdg.processor.service.ProxyService;
import be.kdg.processor.violation.ViolationStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Responsible for processing/handling of messages from the RabbitMq queue.
 */
public class Processor {

    private static final Logger LOGGER = LoggerFactory.getLogger(Processor.class);

    @Autowired
    private XMLMapperService xmlMapperService;

    @Autowired
    private ProcessorMessageHandler processorMessageHandler;

    //TODO: handle exceptions correctly

    @RabbitListener(queues = RabbitConfig.QUEUE_SPECIFIC_NAME)
    public void receiveMessage(final String cameraMessageString) {

        try {

            CameraMessage cameraMessage = xmlMapperService.convertXmlStringToCameraMessage(cameraMessageString);
            LOGGER.info("Received CameraMessage: {}", cameraMessage);

            processorMessageHandler.processMessage(cameraMessage);

        } catch (ObjectMappingException e) {

            LOGGER.warn("Probleem met het mappen van het object.", e);

        }
    }
}
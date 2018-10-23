package be.kdg.processor.business.processing;

import be.kdg.processor.config.RabbitConfig;
import be.kdg.processor.domain.FailedQueueMessage;
import be.kdg.processor.domain.camera.CameraMessage;
import be.kdg.processor.exceptions.ObjectMappingException;
import be.kdg.processor.util.XMLMapperService;
import be.kdg.sa.services.CameraNotFoundException;
import be.kdg.sa.services.LicensePlateNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Responsible for processing/handling of messages from the RabbitMq messageQueue.
 */
public class Processor {

    private static final Logger LOGGER = LoggerFactory.getLogger(Processor.class);

    @Autowired
    private XMLMapperService xmlMapperService;

    @Autowired
    private ProcessorMessageHandler processorMessageHandler;

    @Autowired
    private CopyOnWriteArrayList<FailedQueueMessage> failedQueueMessages;

    @RabbitListener(queues = RabbitConfig.MESSAGE_QUEUE)
    public void receiveMessage(final String cameraMessageString) {

        try {

            CameraMessage cameraMessage = xmlMapperService.convertXmlStringToCameraMessage(cameraMessageString);
            LOGGER.info("Received CameraMessage: {}", cameraMessage);

            processorMessageHandler.processMessage(cameraMessage);

        } catch (ObjectMappingException | IOException | CameraNotFoundException | LicensePlateNotFoundException e) {

            LOGGER.warn("Problem processing CameraMessage {}. Message will be sent to FailedMessageProcessor.", cameraMessageString);
            failedQueueMessages.add(new FailedQueueMessage(cameraMessageString,0));
        }
    }
}
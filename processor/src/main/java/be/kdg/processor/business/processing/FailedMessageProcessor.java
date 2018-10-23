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
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Handles messages that have failed the initial processing attempt by the processor.
 * This will try reading a message two more times, if the message continues to fail,
 * it will be placed on an error queue.
 */
public class FailedMessageProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(FailedMessageProcessor.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private XMLMapperService xmlMapperService;

    @Autowired
    private ProcessorMessageHandler processorMessageHandler;

    @Autowired
    private CopyOnWriteArrayList<FailedQueueMessage> failedQueueMessages;

    @Scheduled(fixedDelay = 10000, initialDelay = 10000)
    public void readFailedMessages() {

        for (FailedQueueMessage failedQueueMessage : failedQueueMessages) {

            // if max read attempts, log on error queue en remove from list
            if (failedQueueMessage.getReadAttempt() >= 3) {

                LOGGER.error("Failed to read CameraMessage 3 times, placing it on the error queue. Message: " + failedQueueMessage.getMessage());
                rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.ROUTING_ERROR_KEY, failedQueueMessage.getMessage());
                failedQueueMessages.remove(failedQueueMessage);
            }

            try {

                LOGGER.info("Retrying failed CameraMessage {}", failedQueueMessage.getMessage());
                CameraMessage cameraMessage = xmlMapperService.convertXmlStringToCameraMessage(failedQueueMessage.getMessage());

                processorMessageHandler.processMessage(cameraMessage);

                // if successful remove it from the failed list
                failedQueueMessages.remove(failedQueueMessage);

            } catch (IOException | ObjectMappingException |LicensePlateNotFoundException | CameraNotFoundException e) {

                // increase read attempt
                failedQueueMessage.setReadAttempt(failedQueueMessage.getReadAttempt() + 1);
                LOGGER.warn("Attempt to read failed message failed again. Attempt #" +failedQueueMessage.getReadAttempt());
            }
        }
    }
}
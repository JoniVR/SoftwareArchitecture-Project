package be.kdg.processor.business.processing;

import be.kdg.processor.config.RabbitConfig;
import be.kdg.processor.domain.camera.CameraMessage;
import be.kdg.processor.exceptions.ObjectMappingException;
import be.kdg.processor.util.XMLMapperService;
import be.kdg.sa.services.CameraNotFoundException;
import be.kdg.sa.services.InvalidLicensePlateException;
import be.kdg.sa.services.LicensePlateNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;

import java.io.IOException;

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
    private RabbitTemplate rabbitTemplate;

    @Retryable(value = { ObjectMappingException.class, IOException.class, CameraNotFoundException.class, InvalidLicensePlateException.class, LicensePlateNotFoundException.class }, backoff = @Backoff(delay = 2000))
    @RabbitListener(queues = RabbitConfig.MESSAGE_QUEUE)
    public void receiveMessage(final String cameraMessageString) throws ObjectMappingException, IOException, CameraNotFoundException, InvalidLicensePlateException, LicensePlateNotFoundException {

        CameraMessage cameraMessage = xmlMapperService.convertXmlStringToCameraMessage(cameraMessageString);
        LOGGER.info("Received: CameraMessage: {}", cameraMessage);

        processorMessageHandler.processMessage(cameraMessage);
    }

    @Recover
    public void recover(Exception exception, String cameraMessageString) {

        LOGGER.error("Error: {} - CameraMessage: {} - Placing on ErrorQueue.", exception.getMessage(), cameraMessageString);
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.ROUTING_ERROR_KEY, cameraMessageString);
    }
}
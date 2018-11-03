package be.kdg.processor.business.processing;

import be.kdg.processor.business.domain.camera.CameraMessage;
import be.kdg.processor.business.domain.settings.Settings;
import be.kdg.processor.business.settings.SettingsListener;
import be.kdg.processor.config.RabbitConfig;
import be.kdg.processor.util.XMLMapperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.RetryContext;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

/**
 * Responsible for processing/handling of messages from the RabbitMq messageQueue.
 */
@EnableRetry
public class Processor implements SettingsListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(Processor.class);

    @Autowired
    private XMLMapperService xmlMapperService;

    @Autowired
    private ProcessorMessageHandler processorMessageHandler;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RetryTemplate retryTemplate;

    @RabbitListener(queues = RabbitConfig.MESSAGE_QUEUE)
    public void receiveMessage(final String cameraMessageString) throws Exception {

        retryTemplate.execute(context -> {

            CameraMessage cameraMessage = xmlMapperService.convertXmlStringToCameraMessage(cameraMessageString);
            LOGGER.info("Received: CameraMessage: {}", cameraMessage);

            processorMessageHandler.processMessage(cameraMessage);
            return null;

        }, retryCallBack -> {

            recover(retryCallBack, cameraMessageString);
            return null;
        });
    }

    @Override
    public void onSettingsChanged(Settings settings) {

        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(settings.getRetryMaxAttempts());

        FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
        backOffPolicy.setBackOffPeriod(settings.getRetryBackOffTimeInMs());

        retryTemplate.setRetryPolicy(retryPolicy);
        retryTemplate.setBackOffPolicy(backOffPolicy);
        LOGGER.info("Updated: Retry settings with: {}", settings);
    }

    private void recover(RetryContext retryContext, String cameraMessageString) {

        LOGGER.error("Error: {} - CameraMessage: {} - Placing on ErrorQueue.", retryContext.getLastThrowable().getMessage(), cameraMessageString);
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.ROUTING_ERROR_KEY, cameraMessageString);
    }
}
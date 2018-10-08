package be.kdg.simulator.simulation;

import be.kdg.simulator.generator.MessageGenerator;
import be.kdg.simulator.model.CameraMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Responsible for providing a link between message generation and message sending.
 */
@Component
public class MessageFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageFactory.class);

    private final MessageGenerator messageGenerator;

    @Autowired
    public MessageFactory(MessageGenerator messageGenerator) {
        this.messageGenerator = messageGenerator;
    }

    public CameraMessage getCameraMessage(){

        CameraMessage cameraMessage = messageGenerator.generate();
        LOGGER.info("A cameramessage was generated: " + cameraMessage);
        return cameraMessage;
    }
}

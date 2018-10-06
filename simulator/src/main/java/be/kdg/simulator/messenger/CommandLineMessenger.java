package be.kdg.simulator.messenger;

import be.kdg.simulator.model.CameraMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "messenger.type", havingValue = "cmd")
public class CommandLineMessenger implements Messenger {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandLineMessenger.class);

    @Override
    public void sendMessage(CameraMessage cameraMessage) {

        LOGGER.info("Placing message on command line.");
        LOGGER.info(cameraMessage.toString());
    }
}

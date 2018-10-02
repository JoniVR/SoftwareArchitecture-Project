package be.kdg.simulator.messengers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "messenger.type", havingValue = "queue")
public class QueueMessenger implements Messenger {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueueMessenger.class);

    @Autowired
    private MessageOriginHandler messageOriginHandler;

    @Override
    public void sendMessage() {

        LOGGER.info("Placing message on queue.");
        messageOriginHandler.relayMessage();
    }
}

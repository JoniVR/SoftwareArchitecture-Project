package be.kdg.simulator.messengers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "messenger.type", havingValue = "cmd")
public class CommandLineMessenger implements Messenger {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandLineMessenger.class);

    @Autowired
    private MessageOriginHandler messageOriginHandler;

    @Override
    public void sendMessage() {

        LOGGER.info(messageOriginHandler.relayMessage().toString());
    }
}

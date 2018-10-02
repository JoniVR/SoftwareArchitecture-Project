package be.kdg.simulator.messengers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "messenger.type", havingValue = "queue")
public class QueueMessenger implements Messenger {

    @Autowired
    private MessageOriginHandler messageOriginHandler;

    @Override
    public void sendMessage() {

        messageOriginHandler.relayMessage();
    }
}

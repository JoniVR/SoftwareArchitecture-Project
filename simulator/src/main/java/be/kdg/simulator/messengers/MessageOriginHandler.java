package be.kdg.simulator.messengers;

import be.kdg.simulator.generators.MessageGenerator;
import be.kdg.simulator.models.CameraMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Provides a link between message generation/source and the Messenger.
 */
@Component
public class MessageOriginHandler {

    @Autowired
    private MessageGenerator messageGenerator;

    public CameraMessage relayMessage(){
        return messageGenerator.generate();
    }
}

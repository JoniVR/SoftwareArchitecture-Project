package be.kdg.simulator.services;

import be.kdg.simulator.messengers.Messenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * The CameraMessageSender is responsible for sending messages to the Queue or command line
 * depending on configuration.
 *
 * @Author Joni Van Roost
 */
@Service
public class CameraMessageSender {

    private final Messenger messenger;

    @Autowired
    public CameraMessageSender(Messenger messenger) {
        this.messenger = messenger;
    }

    @Scheduled(fixedDelay = 3000L)
    public void sendMessage() {

        messenger.sendMessage();
    }
}

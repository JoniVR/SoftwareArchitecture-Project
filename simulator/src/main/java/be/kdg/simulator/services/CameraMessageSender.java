package be.kdg.simulator.services;

import be.kdg.simulator.messengers.Messenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * The CameraMessageSender is responsible for sending messages to the Queue or command line
 * depending on configuration.
 *
 * @Author Joni Van Roost
 */
@Service
public class CameraMessageSender {

    @Autowired
    private Messenger messenger;

    @Scheduled(fixedRate=1000)
    public void run() {

        messenger.sendMessage();
    }
}

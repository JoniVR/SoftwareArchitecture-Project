package be.kdg.simulator.services;

import be.kdg.simulator.messengers.Messenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class MessageSchedulerService {

    @Autowired //Field injection!
    private Messenger messenger;

    @Scheduled(fixedRate=1000)
    public void run() {

        messenger.sendMessage();
    }
}

package be.kdg.simulator.services;

import be.kdg.simulator.messengers.Messenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

// mss service? hoeft in principe ook geen DI te gebruiken denk ik?
@Component
public class MessageSchedulerService {

    @Qualifier("commandLineMessenger")
    @Autowired //Field injection!
    private Messenger messenger;

    @Scheduled(fixedRate=1000)
    public void run() {

        messenger.sendMessage();
    }
}

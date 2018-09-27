package be.kdg.simulator.util;

import be.kdg.simulator.messengers.Messenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class MessageScheduleService {

    @Qualifier("commandLineMessenger")
    @Autowired //Field injection!
    private Messenger messenger;

    @Scheduled(fixedRate=500)
    public void run() {

        messenger.sendMessage();
    }
}

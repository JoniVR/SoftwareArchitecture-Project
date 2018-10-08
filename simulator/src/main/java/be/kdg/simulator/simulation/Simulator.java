package be.kdg.simulator.simulation;

import be.kdg.simulator.messenger.Messenger;
import be.kdg.simulator.model.CameraMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Provides a link between message generation/source and the Messenger.
 */
@EnableScheduling
@Component
public class Simulator {

    private static final Logger LOGGER = LoggerFactory.getLogger(Simulator.class);

    private MessageFactory messageFactory;
    private Messenger messenger;
    private int delay;

    @Autowired
    public Simulator(MessageFactory messageFactory, Messenger messenger) {
        this.messageFactory = messageFactory;
        this.messenger = messenger;
        this.delay = 0;
    }

    //TODO: fix scheduledDelay (make variable somehow?)
    @Scheduled(fixedDelay = 1000)
    public void sendMessage(){

        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            LOGGER.warn("Thread sleep was interrupted.");
            e.printStackTrace();
        }

        CameraMessage cameraMessage = messageFactory.getCameraMessage();
        messenger.sendMessage(cameraMessage);
        delay = cameraMessage.getDelay();
    }
}

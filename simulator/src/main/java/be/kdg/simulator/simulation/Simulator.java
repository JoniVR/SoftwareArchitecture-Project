package be.kdg.simulator.simulation;

import be.kdg.simulator.generator.MessageGenerator;
import be.kdg.simulator.messenger.Messenger;
import be.kdg.simulator.model.CameraMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * Provides a link between message generation/source and the Messenger.
 */
@Component
// used to prevent the CommandLineRunner from working inside unit tests
@ConditionalOnProperty(prefix = "job.autorun", name = "enabled", havingValue = "true", matchIfMissing = true)
public class Simulator implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(Simulator.class);

    private final MessageGenerator messageGenerator;
    private final Messenger messenger;
    private int delay;

    @Autowired
    public Simulator(MessageGenerator messageGenerator, Messenger messenger) {
        this.messageGenerator = messageGenerator;
        this.messenger = messenger;
        this.delay = 0;
    }

    // Executed at startup
    @Override
    public void run(String... args) {

        CameraMessage cameraMessage = messageGenerator.generate();

        while ((cameraMessage != null)) {

            LOGGER.info("A message was generated: " + cameraMessage);

            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                LOGGER.warn("Thread sleep was interrupted.", e);
            }

            messenger.sendMessage(cameraMessage);
            delay = cameraMessage.getDelay();

            cameraMessage = messageGenerator.generate();
        }

        System.exit(0);
    }
}

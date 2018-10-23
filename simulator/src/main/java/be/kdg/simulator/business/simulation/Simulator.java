package be.kdg.simulator.business.simulation;

import be.kdg.simulator.business.generator.MessageGenerator;
import be.kdg.simulator.business.messenger.Messenger;
import be.kdg.simulator.domain.CameraMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Optional;

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
    private final ConfigurableApplicationContext context;
    private int delay;

    @Autowired
    public Simulator(MessageGenerator messageGenerator, Messenger messenger, ConfigurableApplicationContext context) {
        this.messageGenerator = messageGenerator;
        this.messenger = messenger;
        this.context = context;
        this.delay = 0;
    }

    // Executed at startup
    @Override
    public void run(String... args) {

        Optional<CameraMessage> cameraMessage = messageGenerator.generate();

        while ((cameraMessage.isPresent())) {

            LOGGER.info("A message was generated: " + cameraMessage.get());

            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                LOGGER.warn("Thread sleep was interrupted.", e);
            }

            messenger.sendMessage(cameraMessage.get());
            delay = cameraMessage.get().getDelay();

            cameraMessage = messageGenerator.generate();
        }
        System.exit(SpringApplication.exit(context));
    }
}

package be.kdg.simulator.business.simulation;

import be.kdg.simulator.business.domain.CameraMessage;
import be.kdg.simulator.business.generator.MessageGenerator;
import be.kdg.simulator.business.messenger.Messenger;
import be.kdg.simulator.exceptions.QueueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Provides a link between message generation/source and the Messenger.
 */
@Component
// used to prevent the CommandLineRunner from working inside unit tests
@ConditionalOnProperty(prefix = "job.autorun", name = "enabled", havingValue = "true", matchIfMissing = true)
public class Simulator implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(Simulator.class);

    @Autowired
    private MessageGenerator messageGenerator;
    @Autowired
    private Messenger messenger;

    // Executed at startup
    @Override
    public void run(String... args) {

        try {

            Optional<CameraMessage> cameraMessage = messageGenerator.generate();

            while ((cameraMessage.isPresent())) {

                CameraMessage message = cameraMessage.get();
                LOGGER.info("A message was generated: {}", message);

                Thread.sleep(message.getDelay());

                message.setTimestamp(LocalDateTime.now());

                messenger.sendMessage(message);
                System.out.println("ITS ME");

                cameraMessage = messageGenerator.generate();
            }

        } catch (IOException | QueueException | InterruptedException e) {

            LOGGER.error("Error trying to send message. ErrorMessage: {}", e.getMessage());
        }
    }
}
package be.kdg.simulator.business.simulation;

import be.kdg.simulator.business.generator.MessageGenerator;
import be.kdg.simulator.business.messenger.Messenger;
import be.kdg.simulator.domain.CameraMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import java.io.IOException;
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
    private int delay;

    @Autowired
    public Simulator(MessageGenerator messageGenerator, Messenger messenger) {
        this.messageGenerator = messageGenerator;
        this.messenger = messenger;
        this.delay = 0;
    }

    // Executed at startup
    @Retryable(value = {AmqpException.class, IOException.class, InterruptedException.class, IllegalArgumentException.class}, backoff = @Backoff(delay = 5000))
    @Override
    public void run(String... args) throws IOException, AmqpException, InterruptedException, IllegalArgumentException {

        Optional<CameraMessage> cameraMessage = messageGenerator.generate();

        while ((cameraMessage.isPresent())) {

            LOGGER.info("A message was generated: " + cameraMessage.get());

            Thread.sleep(delay);

            messenger.sendMessage(cameraMessage.get());
            delay = cameraMessage.get().getDelay();

            cameraMessage = messageGenerator.generate();
        }
    }

    @Recover
    private void recover(AmqpException e, CameraMessage message) {

        LOGGER.error("Error trying to place message on queue. Error: {} - Message: {}", e.getMessage(), message);
    }
}
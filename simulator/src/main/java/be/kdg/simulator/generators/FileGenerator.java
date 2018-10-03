package be.kdg.simulator.generators;

import be.kdg.simulator.models.CameraMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * This mode will be used to simulate traffic violations.
 * The messages that will be sent won't be generated at random but will be read from a text file at a custom location.
 * In this file, each message item will be separated by a comma (cameraID, licensePlate, delay).
 * The delay (in ms) determines how long we should wait after each message.
 *
 * @Author Joni Van Roost
 * @Version 0.0.1
 */
@Component
@ConditionalOnProperty(name = "generator.type", havingValue = "file")
public class FileGenerator implements MessageGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileGenerator.class);

    @Override
    public CameraMessage generate() {

        LOGGER.info("CameraMessage was generated from file.");
        return null;
    }
}

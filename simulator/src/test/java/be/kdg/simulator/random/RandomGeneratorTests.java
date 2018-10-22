package be.kdg.simulator.random;

import be.kdg.simulator.business.generator.MessageGenerator;
import be.kdg.simulator.domain.CameraMessage;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
// prevent the CommandLineRunner from working inside unit tests
@SpringBootTest(properties = {"job.autorun.enabled=false"})
public class RandomGeneratorTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(RandomGeneratorTests.class);

    @Autowired
    private MessageGenerator messageGenerator;

    /**
     * Tests is the format of the license plates is correct.
     */
    @Test
    public void testRandomLicensePlateFormat() {

        Optional<CameraMessage> cameraMessage = messageGenerator.generate();

        // make sure camera object can be generated correctly
        Assert.assertTrue(cameraMessage.isPresent());

        // regex for EU plates
        String expectedRegex = "^[0-9]-[A-Z]{3}-[0-9]{3}$";

        // Check if generated plate matches with regex
        Assert.assertTrue("License plate does not have the correct format.",
                cameraMessage.get().getLicenseplate().matches(expectedRegex));

        LOGGER.info("testRandomLicensePlateFormat - Tested license plate: " + cameraMessage.get().getLicenseplate());
    }
}

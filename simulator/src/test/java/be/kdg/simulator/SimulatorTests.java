package be.kdg.simulator;

import be.kdg.simulator.generator.MessageGenerator;
import be.kdg.simulator.generator.RandomMessageGenerator;
import be.kdg.simulator.model.CameraMessage;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.slf4j.Logger;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SimulatorTests {

    @Autowired //Field injection!
    private MessageGenerator messageGenerator;

    private static final Logger LOGGER = LoggerFactory.getLogger(SimulatorTests.class);

    /**
     * Tests is the format of the license plates is correct.
     */
    @Test
    public void testRandomLicensePlateFormat() {

        CameraMessage cameraMessage = messageGenerator.generate();

        // regex for EU plates
        String expectedRegex = "^[0-9]-[A-Z]{3}-[0-9]{3}$";

        // Check if generated plate matches with regex
        Assert.assertTrue("License plate does not have the correct format.",
                cameraMessage.getLicenseplate().matches(expectedRegex));

        LOGGER.info("testRandomLicensePlateFormat - Tested license plate: " + cameraMessage.getLicenseplate());
    }

    /**
     * Tests if the camera ID is between the correct bounds.
     * Might not always be a valid test since random ids can fit bounds randomly
     * (so even if the code that is generating the ID is wrong, the test could still succeed because it fits the bound "by accident).
     * But I figured it would never hurt to add an extra check.
     */
    @Test
    public void testRandomLicensePlateCameraIdBounds(){

        CameraMessage cameraMessage = messageGenerator.generate();

        RandomMessageGenerator randomMessageGenerator = (RandomMessageGenerator) messageGenerator;

        int maxCameraId = randomMessageGenerator.getMaxCameraId();
        int camId = cameraMessage.getId();

        Assert.assertTrue("Random cameraID does not fit correct bounds.",
                (camId <= maxCameraId) && camId > 0);

        LOGGER.info("testRandomLicensePlateCameraIdBounds - Tested cameraId: " + camId);
    }
}

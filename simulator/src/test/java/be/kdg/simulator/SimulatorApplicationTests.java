package be.kdg.simulator;

import be.kdg.simulator.generators.MessageGenerator;
import be.kdg.simulator.generators.RandomMessageGenerator;
import be.kdg.simulator.model.CameraMessage;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SimulatorApplicationTests {

    @Qualifier("randomMessageGenerator")
    @Autowired //Field injection!
    private MessageGenerator messageGenerator;

    //TODO: possibly move these tests to own "messageGeneratortests"?

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
    }

    /**
     * Tests if the camera ID is between the correct bounds.
     */
    @Test
    public void testRandomLicensePlateCameraIdBounds(){

        CameraMessage cameraMessage = messageGenerator.generate();

        RandomMessageGenerator randomMessageGenerator = (RandomMessageGenerator) messageGenerator;

        int maxCameraId = randomMessageGenerator.getMaxCameraId();
        int camId = cameraMessage.getId();

        Assert.assertTrue("Random cameraID does not fit correct bounds.",
                (camId <= maxCameraId) && camId > 0);
    }
}

package be.kdg.simulator;

import be.kdg.simulator.generators.MessageGenerator;
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

    @Test
    public void testRandomLicensePlate() {

        CameraMessage cameraMessage = messageGenerator.generate();

        // regex for EU plates
        String expectedRegex = "^[0-9]{1}-[A-Z]{3}-[0-9]{3}$";

        // Check if generated plate matches with regex
        Assert.assertTrue("License plate does not have the correct format.",
                cameraMessage.getLicenseplate().matches(expectedRegex));
    }
}

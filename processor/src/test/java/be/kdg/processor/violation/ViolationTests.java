package be.kdg.processor.violation;

import be.kdg.processor.model.camera.Camera;
import be.kdg.processor.model.camera.CameraType;
import be.kdg.processor.model.camera.Location;
import be.kdg.processor.model.camera.Segment;
import be.kdg.processor.model.vehicle.Vehicle;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ViolationTests {

    @Autowired
    private EmissionViolation emissionViolation;

    @Test
    public void testEmissionViolation() {

        Camera testCamera = new Camera(1, CameraType.EMISSION, new Location(),3,new Segment());
        final String failMessage = "Violation detection is not working properly.";

        // test violation detected
        Vehicle testvehicleViolation = new Vehicle("1-ABC-123","47.11.10-171.40",1);

        boolean isViolation = emissionViolation.detect(testCamera, testvehicleViolation);

        Assert.assertTrue(failMessage, isViolation);

        // test no violation detected
        Vehicle testVehicleNoViolation = new Vehicle("1-ABC-123","47.11.10-171.40",3);

        isViolation = emissionViolation.detect(testCamera, testVehicleNoViolation);

        Assert.assertFalse(failMessage, isViolation);
    }
}
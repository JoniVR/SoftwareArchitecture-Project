package be.kdg.processor.violation;

import be.kdg.processor.model.camera.Camera;
import be.kdg.processor.model.camera.CameraType;
import be.kdg.processor.model.camera.Location;
import be.kdg.processor.model.camera.Segment;
import be.kdg.processor.model.fine.Fine;
import be.kdg.processor.model.vehicle.Vehicle;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FineTests {

    @Autowired
    private EmissionViolation emissionViolation;

    @Test
    public void testEmissionFineCalculation(){

        Camera testCamera = new Camera(1, CameraType.EMISSION, new Location(),3,new Segment());
        Vehicle testvehicleViolation = new Vehicle("1-ABC-123","47.11.10-171.40",1);

        Fine fine = emissionViolation.calculateFine(testCamera, testvehicleViolation);

        Assert.assertNotNull("Fine is null.", fine);
    }
}

package be.kdg.processor.business.violation;

import be.kdg.processor.domain.camera.Camera;
import be.kdg.processor.domain.camera.CameraType;
import be.kdg.processor.domain.camera.Location;
import be.kdg.processor.domain.camera.Segment;
import be.kdg.processor.domain.fine.Fine;
import be.kdg.processor.domain.fine.FineFactor;
import be.kdg.processor.domain.vehicle.Vehicle;
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
        Vehicle testVehicleViolation = new Vehicle("1-ABC-123","47.11.10-171.40",1);

        Fine fine = emissionViolation.calculateFine(testCamera, testVehicleViolation);

        Assert.assertNotNull("Fine is null.", fine);

        // test amount
        FineFactor fineFactor = new FineFactor();
        double amount = fineFactor.getEmissionFactor();
        Assert.assertEquals("Fine price is not equal.",fine.getAmount(), amount,0);
    }
}

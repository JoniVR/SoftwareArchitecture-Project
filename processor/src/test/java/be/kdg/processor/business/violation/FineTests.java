package be.kdg.processor.business.violation;

import be.kdg.processor.domain.camera.Camera;
import be.kdg.processor.domain.camera.Location;
import be.kdg.processor.domain.camera.ProcessedCameraMessage;
import be.kdg.processor.domain.camera.Segment;
import be.kdg.processor.domain.fine.Fine;
import be.kdg.processor.domain.fine.FineFactor;
import be.kdg.processor.domain.vehicle.Vehicle;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FineTests {

    @Autowired
    private EmissionViolation emissionViolation;

    private Camera testCamera;
    private Vehicle testVehicleViolation;
    private ProcessedCameraMessage processedCameraMessage;
    private FineFactor fineFactor;

    @Before
    public void setUp() {

        testCamera = new Camera(1, new Location(),3,new Segment());
        testVehicleViolation = new Vehicle("1-ABC-123","47.11.10-171.40",1);
        processedCameraMessage = new ProcessedCameraMessage(testVehicleViolation, testCamera, LocalDateTime.now());
        fineFactor = new FineFactor();
    }

    @After
    public void tearDown() {
        testCamera = null;
        testVehicleViolation = null;
        processedCameraMessage = null;
    }

    @Test
    public void testEmissionFineCalculation(){

        Fine fine = emissionViolation.calculateFine(processedCameraMessage);

        Assert.assertNotNull("Fine is null.", fine);

        // test amount
        double amount = fineFactor.getEmissionFactor();
        Assert.assertEquals("Fine price is not equal.",fine.getAmount(), amount,0);
    }
}

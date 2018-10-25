package be.kdg.processor.business.violation;

import be.kdg.processor.domain.camera.*;
import be.kdg.processor.domain.fine.Fine;
import be.kdg.processor.domain.fine.FineType;
import be.kdg.processor.domain.vehicle.Vehicle;
import be.kdg.processor.service.FineFactorService;
import be.kdg.processor.service.FineService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ViolationTests {

    @Autowired
    private EmissionViolation emissionViolation;

    @Autowired
    private FineService fineService;

    @Autowired
    private FineFactorService fineFactorService;

    private Camera testCamera;
    private Vehicle vehicle;
    private ProcessedCameraMessage processedCameraMessage;
    private Fine fine;

    @Before
    public void setUp() {

        testCamera = new Camera(1, new Location(),3,new Segment());
        vehicle = new Vehicle("1-ABC-123","47.11.10-171.40",1);
        processedCameraMessage = new ProcessedCameraMessage(vehicle, testCamera, LocalDateTime.now());
        fine = new Fine(1000.0, FineType.EMISSION, false, null, vehicle.getPlateId(), testCamera.getId());
    }

    @After
    public void tearDown(){
        testCamera = null;
        vehicle = null;
        processedCameraMessage = null;
        fine = null;
    }

    @Test
    public void testEmissionViolation() {

        // test violation detected
        boolean isViolation = emissionViolation.detect(processedCameraMessage);

        Assert.assertTrue(isViolation);

        // test no violation detected
        Vehicle testVehicleNoViolation = new Vehicle("1-ABC-123","47.11.10-171.40",3);
        processedCameraMessage.setVehicle(testVehicleNoViolation);

        isViolation = emissionViolation.detect(processedCameraMessage);

        Assert.assertFalse(isViolation);
    }

    /**
     * Tests if a double violation within a given timeframe does not create a new fine.
     */
    @Transactional
    @Test
    public void testDoubleEmissionViolation() {

        fine.setCreationDate(LocalDateTime.now().minusHours(1));
        fineService.save(fine);

        // since we're testing if double emission violations get detected and prevented, this should be false
        boolean isViolation = emissionViolation.detect(processedCameraMessage);

        Assert.assertFalse(isViolation);
    }

    /**
     * Tests if a double violation outside of a given timeframe does not create a new fine.
     * Given timeframe might need some adjustments when changed (currently still hardcoded)
     * TODO: make timeframe not hardcoded and change hardcoded value here.
     */
    @Transactional
    @Test
    public void testNoDoubleEmissionViolation() {

        int fixedTimeFrame = fineFactorService.loadFineFactor().getEmissionTimeFrameInHours();

        fine.setCreationDate(LocalDateTime.now().minusHours(fixedTimeFrame + 1));
        fineService.save(fine);

        // since we're testing if double emission violations get detected and prevented, this should be true
        boolean isViolation = emissionViolation.detect(processedCameraMessage);

        Assert.assertTrue(isViolation);
    }
}
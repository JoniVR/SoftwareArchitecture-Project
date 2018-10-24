package be.kdg.processor.business.violation;

import be.kdg.processor.domain.camera.Camera;
import be.kdg.processor.domain.camera.CameraType;
import be.kdg.processor.domain.camera.Location;
import be.kdg.processor.domain.camera.Segment;
import be.kdg.processor.domain.fine.Fine;
import be.kdg.processor.domain.fine.FineType;
import be.kdg.processor.domain.vehicle.Vehicle;
import be.kdg.processor.service.FineFactorService;
import be.kdg.processor.service.FineService;
import org.junit.Assert;
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

    /**
     * Tests if a double violation within a given timeframe does not create a new fine.
     */
    @Transactional
    @Test
    public void testDoubleEmissionViolation() {

        Camera testCamera = new Camera(1, CameraType.EMISSION, new Location(),3, new Segment());
        Vehicle vehicle = new Vehicle("1-ABC-123", "47.11.10-171.40", 1);

        Fine fine = new Fine(1000.0, FineType.EMISSION, false, null, vehicle.getPlateId(), testCamera.getId());
        fine.setCreationDate(LocalDateTime.now().minusHours(1));
        fineService.save(fine);

        // since we're testing if double emission violations get detected and prevented, this should be false
        boolean isViolation = emissionViolation.detect(testCamera, vehicle);

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

        Camera testCamera = new Camera(1, CameraType.EMISSION, new Location(),3, new Segment());
        Vehicle vehicle = new Vehicle("1-ABC-123", "47.11.10-171.40", 1);

        Fine fine = new Fine(1000.0, FineType.EMISSION, false, null, vehicle.getPlateId(), testCamera.getId());
        fine.setCreationDate(LocalDateTime.now().minusHours(fixedTimeFrame + 1));
        fineService.save(fine);

        // since we're testing if double emission violations get detected and prevented, this should be true
        boolean isViolation = emissionViolation.detect(testCamera, vehicle);

        Assert.assertTrue(isViolation);
    }
}
package be.kdg.processor.business.violation;

import be.kdg.processor.business.domain.camera.Camera;
import be.kdg.processor.business.domain.camera.Location;
import be.kdg.processor.business.domain.camera.ProcessedCameraMessage;
import be.kdg.processor.business.domain.camera.Segment;
import be.kdg.processor.business.domain.vehicle.Vehicle;
import be.kdg.processor.business.domain.violation.Violation;
import be.kdg.processor.business.domain.violation.ViolationType;
import be.kdg.processor.business.service.FineFactorService;
import be.kdg.processor.business.service.ViolationService;
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
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ViolationTests {

    @Autowired
    private EmissionViolation emissionViolation;

    @Autowired
    private ViolationService violationService;

    @Autowired
    private FineFactorService fineFactorService;

    private Camera testCamera;
    private Vehicle vehicle;
    private ProcessedCameraMessage processedCameraMessage;
    private Violation violation;

    @Before
    public void setUp() {

        testCamera = new Camera(1, new Location(),3, new Segment(2, 100,2));
        vehicle = new Vehicle("1-ABC-123","47.11.10-171.40",1);
        processedCameraMessage = new ProcessedCameraMessage(vehicle, testCamera, LocalDateTime.now());
        violation = new Violation(ViolationType.EMISSION, null, null, vehicle.getEuroNumber(), vehicle.getPlateId(), LocalDateTime.now(), 2, 1);
    }

    @After
    public void tearDown(){
        testCamera = null;
        vehicle = null;
        processedCameraMessage = null;
        violation = null;
    }

    @Test
    public void testEmissionViolation() {

        // test violation detected
        Optional<Violation> violationOptional = emissionViolation.detect(processedCameraMessage);

        Assert.assertTrue(violationOptional.isPresent());

        // test no violation detected
        Vehicle testVehicleNoViolation = new Vehicle("1-ABC-123","47.11.10-171.40",3);
        processedCameraMessage.setVehicle(testVehicleNoViolation);

        violationOptional = emissionViolation.detect(processedCameraMessage);

        Assert.assertFalse(violationOptional.isPresent());
    }

    /**
     * Tests if a double violation within a given timeframe does not create a new violation.
     */
    @Transactional
    @Test
    public void testDoubleEmissionViolation() {

        violation.setCreationDate(LocalDateTime.now().minusHours(1));
        violationService.addViolation(violation);

        // since we're testing if double emission violations get detected and prevented
        Optional<Violation> violationOptional = emissionViolation.detect(processedCameraMessage);

        Assert.assertFalse(violationOptional.isPresent());
    }

    /**
     * Tests if a double violation outside of a given timeframe does not create a new violation.
     */
    @Transactional
    @Test
    public void testNoDoubleEmissionViolation() {

        int fixedTimeFrame = fineFactorService.loadFineFactor().getEmissionTimeFrameInHours();

        violation.setCreationDate(LocalDateTime.now().minusHours(fixedTimeFrame + 1));
        violationService.addViolation(violation);

        // since we're testing if double emission violations get detected and prevented
        Optional<Violation> violationOptional = emissionViolation.detect(processedCameraMessage);

        Assert.assertTrue(violationOptional.isPresent());
    }
}
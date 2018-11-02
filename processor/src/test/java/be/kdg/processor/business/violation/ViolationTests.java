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
    private SpeedingViolation speedingViolation;

    @Autowired
    private ViolationService violationService;

    @Autowired
    private FineFactorService fineFactorService;

    private Camera testCamera1;
    private Camera testCamera2;
    private Vehicle vehicle;
    private ProcessedCameraMessage processedCameraMessage1;
    private ProcessedCameraMessage processedCameraMessage2;
    private Violation violation;

    @Before
    public void setUp() {

        testCamera1 = new Camera(1, new Location(), 3, new Segment(1000, 100, 2));
        vehicle = new Vehicle("1-ABC-123", "47.11.10-171.40", 1);
        processedCameraMessage1 = new ProcessedCameraMessage(vehicle, testCamera1, LocalDateTime.now());
        violation = new Violation(ViolationType.EMISSION, null, null, vehicle.getEuroNumber(), vehicle.getPlateId(), LocalDateTime.now(), 2, 1);
    }

    @After
    public void tearDown() {
        testCamera1 = null;
        vehicle = null;
        processedCameraMessage1 = null;
        violation = null;
    }

    @Test
    public void testEmissionViolation() {

        // test violation detected
        Optional<Violation> violationOptional = emissionViolation.detect(processedCameraMessage1);

        Assert.assertTrue(violationOptional.isPresent());

        // test no violation detected
        Vehicle testVehicleNoViolation = new Vehicle("1-ABC-123", "47.11.10-171.40", 3);
        processedCameraMessage1.setVehicle(testVehicleNoViolation);

        violationOptional = emissionViolation.detect(processedCameraMessage1);

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
        Optional<Violation> violationOptional = emissionViolation.detect(processedCameraMessage1);

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
        Optional<Violation> violationOptional = emissionViolation.detect(processedCameraMessage1);

        Assert.assertTrue(violationOptional.isPresent());
    }

    @Test
    @Transactional
    public void testSpeedingViolation() {

        testCamera2 = new Camera(2, new Location(), 3, null);
        processedCameraMessage2 = new ProcessedCameraMessage(vehicle, testCamera2, processedCameraMessage1.getTimeStamp().plusSeconds(1));

        // We need to run a first message to the detection method because it needs to be buffered first
        speedingViolation.detect(processedCameraMessage1);
        Optional<Violation> violationOptional1 = speedingViolation.detect(processedCameraMessage2);

        Assert.assertTrue(violationOptional1.isPresent());

        // Test if not a violation
        processedCameraMessage2.setTimeStamp(processedCameraMessage1.getTimeStamp().plusHours(10));
        Optional<Violation> violationOptional2 = speedingViolation.detect(processedCameraMessage2);

        Assert.assertFalse(violationOptional2.isPresent());
    }
}
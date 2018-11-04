package be.kdg.processor.business.service;

import be.kdg.processor.business.domain.camera.Camera;
import be.kdg.processor.business.domain.camera.Location;
import be.kdg.processor.business.domain.camera.Segment;
import be.kdg.processor.business.domain.vehicle.Vehicle;
import be.kdg.processor.business.domain.violation.Violation;
import be.kdg.processor.business.domain.violation.ViolationType;
import be.kdg.processor.exceptions.ViolationException;
import be.kdg.processor.persistence.ViolationRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ViolationServiceTests {

    @Mock
    private ViolationRepository violationRepository;

    private ViolationService violationServiceUnderTest;

    private Violation violation;
    private Violation violation1;
    private Violation violation2;
    private Violation violation3;

    private Vehicle vehicle;
    private Camera camera;

    @Before
    public void setUp() {

        initMocks(this);

        violationServiceUnderTest = new ViolationService(violationRepository);

        camera = new Camera(1, new Location(),3, new Segment());
        vehicle = new Vehicle("1-ABC-123", "47.11.10-171.40", 1);

        violation = new Violation(ViolationType.EMISSION, null, null, null, vehicle.getPlateId(), LocalDateTime.now().plusHours(1), camera.getId(), 2);
        violation1 = new Violation(ViolationType.EMISSION, null, null, null, vehicle.getPlateId(), LocalDateTime.now().minusHours(30), camera.getId(), 2);
        violation2 = new Violation(ViolationType.EMISSION, null, null, null, vehicle.getPlateId(), LocalDateTime.now().minusHours(1), camera.getId(), 2);
        violation3 = new Violation(ViolationType.EMISSION, null, null, null, vehicle.getPlateId(), LocalDateTime.now().plusHours(30), camera.getId(), 2);
    }

    public void tearDown() {

        camera = null;
        vehicle = null;

        violation = null;
        violation1 = null;
        violation2 = null;
        violation3 = null;
    }

    @Test
    public void testAddViolation() {

        // Setup
        Mockito.when(violationRepository.save(violation))
                .thenReturn(violation);

        // run the test
        Violation violationResult = violationServiceUnderTest.addViolation(violation);

        // Verify the results
        Assert.assertNotNull(violationResult);
    }

    @Test
    public void testLoadViolation() throws ViolationException {

        // Setup
        Mockito.when(violationRepository.findById(violation.getId()))
                .thenReturn(Optional.ofNullable(violation));

        // run the test
        Violation violationResult = violationServiceUnderTest.loadViolation(violation.getId());

        // verify the results
        Assert.assertNotNull(violationResult);
    }

    @Transactional
    @Test
    public void testLoadLatestViolationFrom() {

        // Setup
        Mockito.when(violationRepository.findFirstByLicensePlateOrderByCreationDateDesc(vehicle.getPlateId()))
                .thenReturn(Optional.of(violation3));

        // run the test
        Optional<Violation> optionalViolation = violationServiceUnderTest.loadLatestViolationFrom(vehicle.getPlateId());

        // verify the results
        Assert.assertTrue(optionalViolation.isPresent());

        Violation violationToTest = optionalViolation.get();

        Assert.assertEquals(violation3, violationToTest);
    }
}

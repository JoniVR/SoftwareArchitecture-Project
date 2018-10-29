package be.kdg.processor.business.service;

import be.kdg.processor.business.domain.camera.Camera;
import be.kdg.processor.business.domain.camera.Location;
import be.kdg.processor.business.domain.camera.Segment;
import be.kdg.processor.business.domain.vehicle.Vehicle;
import be.kdg.processor.business.domain.violation.Violation;
import be.kdg.processor.business.domain.violation.ViolationType;
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
public class ViolationServiceTests {

    @Autowired
    private ViolationService violationService;

    private Violation violation;
    private Violation violation1;
    private Violation violation2;
    private Violation violation3;

    private Vehicle vehicle;
    private Camera camera;

    @Before
    public void setUp() {

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

    @Transactional
    @Test
    public void testLoadLatestViolationFrom() {

        violationService.save(violation);
        violationService.save(violation1);
        violationService.save(violation2);
        violationService.save(violation3);

        Optional<Violation> violationOptional = violationService.loadLatestViolationFrom(vehicle.getPlateId());

        Assert.assertTrue(violationOptional.isPresent());

        Violation violationToTest = violationOptional.get();

        Assert.assertEquals(violation3, violationToTest);
    }
}

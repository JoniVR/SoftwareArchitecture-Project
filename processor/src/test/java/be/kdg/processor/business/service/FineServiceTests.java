package be.kdg.processor.business.service;

import be.kdg.processor.business.domain.camera.Camera;
import be.kdg.processor.business.domain.camera.Location;
import be.kdg.processor.business.domain.camera.Segment;
import be.kdg.processor.business.domain.fine.Fine;
import be.kdg.processor.business.domain.vehicle.Vehicle;
import be.kdg.processor.business.domain.violation.Violation;
import be.kdg.processor.business.domain.violation.ViolationType;
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
public class FineServiceTests {

    @Autowired
    private FineService fineService;

    private Camera testCamera;
    private Vehicle vehicle;
    private Fine fineToSave;

    @Before
    public void setUp() {

        testCamera = new Camera(1, new Location(),3, new Segment());
        vehicle = new Vehicle("1-ABC-123", "47.11.10-171.40", 1);

        Violation violation = new Violation(ViolationType.EMISSION, null, null, vehicle.getEuroNumber(), vehicle.getPlateId(), LocalDateTime.now(), testCamera.getId(), 2);
        fineToSave = new Fine(10.0, false, null, violation);
    }

    @After
    public void tearDown() {

        testCamera = null;
        vehicle = null;
        fineToSave = null;
    }

    @Transactional
    @Test
    public void testSaveFine() {

        Fine savedFine = fineService.save(fineToSave);
        Assert.assertNotNull("Fine id is null", savedFine.getId());
    }
}

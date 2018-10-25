package be.kdg.processor.service;

import be.kdg.processor.domain.camera.Camera;
import be.kdg.processor.domain.camera.Location;
import be.kdg.processor.domain.camera.Segment;
import be.kdg.processor.domain.fine.Fine;
import be.kdg.processor.domain.fine.FineType;
import be.kdg.processor.domain.vehicle.Vehicle;
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
public class FineServiceTests {

    @Autowired
    private FineService fineService;

    private Camera testCamera;
    private Vehicle vehicle;
    private Fine fineToSave;
    private Fine fine;
    private Fine fine1;
    private Fine fine2;
    private Fine fine3;


    @Before
    public void setUp() {

        testCamera = new Camera(1, new Location(),3, new Segment());
        vehicle = new Vehicle("1-ABC-123", "47.11.10-171.40", 1);

        fineToSave = new Fine( 10.0, FineType.EMISSION, true, "Dit is een test.", "1-ABC-123", 1);
        fine = new Fine(1L, 1000.0, FineType.EMISSION, false, null, vehicle.getPlateId(), LocalDateTime.now().plusHours(1), testCamera.getId());
        fine1 = new Fine(2L, 1000.0, FineType.EMISSION, false, null, vehicle.getPlateId(), LocalDateTime.now().minusHours(30), testCamera.getId());
        fine2 = new Fine(3L, 1000.0, FineType.EMISSION, false, null, vehicle.getPlateId(), LocalDateTime.now().minusHours(1), testCamera.getId());
        fine3 = new Fine(4L, 1000.0, FineType.EMISSION, false, null, vehicle.getPlateId(), LocalDateTime.now().plusHours(30), testCamera.getId());
    }

    @After
    public void tearDown() {

        testCamera = null;
        vehicle = null;

        fineToSave = null;
        fine = null;
        fine1 = null;
        fine2 = null;
        fine3 = null;
    }

    @Transactional
    @Test
    public void testSaveFine() {

        Fine savedFine = fineService.save(fineToSave);
        Assert.assertNotNull("Fine id is null", savedFine.getId());
    }

    @Transactional
    @Test
    public void testLoadLatestFineFrom() {

        fineService.save(fine);
        fineService.save(fine1);
        fineService.save(fine2);
        fineService.save(fine3);

        Optional<Fine> fineOptional = fineService.loadLatestFineFrom(vehicle.getPlateId());

        Assert.assertTrue(fineOptional.isPresent());

        Fine fineToTest = fineOptional.get();

        Assert.assertEquals(fine3, fineToTest);
    }
}

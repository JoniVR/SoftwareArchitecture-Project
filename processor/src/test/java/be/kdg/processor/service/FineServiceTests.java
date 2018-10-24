package be.kdg.processor.service;

import be.kdg.processor.domain.camera.Camera;
import be.kdg.processor.domain.camera.CameraType;
import be.kdg.processor.domain.camera.Location;
import be.kdg.processor.domain.camera.Segment;
import be.kdg.processor.domain.fine.Fine;
import be.kdg.processor.domain.fine.FineType;
import be.kdg.processor.domain.vehicle.Vehicle;
import org.junit.Assert;
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

    @Transactional
    @Test
    public void testSaveFine() {

        Fine fine = new Fine( 10.0, FineType.EMISSION, true, "Dit is een test.", "1-ABC-123", 1);
        Fine savedFine = fineService.save(fine);
        Assert.assertNotNull("Fine id is null", savedFine.getId());
    }

    @Transactional
    @Test
    public void testLoadLatestFineFrom() {

        Camera testCamera = new Camera(1, CameraType.EMISSION, new Location(),3, new Segment());
        Vehicle vehicle = new Vehicle("1-ABC-123", "47.11.10-171.40", 1);

        Fine fine = new Fine(1L, 1000.0, FineType.EMISSION, false, null, vehicle.getPlateId(), LocalDateTime.now().plusHours(1), testCamera.getId());
        fineService.save(fine);

        Fine fine1 = new Fine(2L, 1000.0, FineType.EMISSION, false, null, vehicle.getPlateId(), LocalDateTime.now().minusHours(30), testCamera.getId());
        fineService.save(fine1);

        Fine fine2 = new Fine(3L, 1000.0, FineType.EMISSION, false, null, vehicle.getPlateId(), LocalDateTime.now().minusHours(1), testCamera.getId());
        fineService.save(fine2);

        Fine fine3 = new Fine(4L, 1000.0, FineType.EMISSION, false, null, vehicle.getPlateId(), LocalDateTime.now().plusHours(30), testCamera.getId());
        fineService.save(fine3);

        Optional<Fine> fineOptional = fineService.loadLatestFineFrom(vehicle.getPlateId());

        Assert.assertTrue(fineOptional.isPresent());

        Fine fineToTest = fineOptional.get();

        Assert.assertEquals(fine3, fineToTest);
    }
}

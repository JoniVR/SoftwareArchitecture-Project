package be.kdg.processor.business.service;

import be.kdg.processor.business.domain.camera.Camera;
import be.kdg.processor.business.domain.vehicle.Vehicle;
import be.kdg.processor.exceptions.ObjectMappingException;
import be.kdg.sa.services.InvalidLicensePlateException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProxyServiceTests {

    @Autowired
    public ProxyService proxyService;

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test(expected = IOException.class)
    public void testCameraService() throws IOException, ObjectMappingException {

        Camera camera = proxyService.getCameraObject(2);

        // test if getting object works
        Assert.assertNotNull(camera);
        Assert.assertEquals(2, camera.getId());

        proxyService.getCameraObject(101);
    }

    @Test(expected = InvalidLicensePlateException.class)
    public void testLicensePlateService() throws IOException, ObjectMappingException {

        String correctPlate = "1-ABC-123";
        String incorrectPlate = "-ABC-123";

        // Test object returned from service
        Vehicle vehicle = proxyService.getVehicleObject(correctPlate);
        Assert.assertNotNull(vehicle);
        Assert.assertEquals(vehicle.getPlateId(), correctPlate);

        proxyService.getVehicleObject(incorrectPlate);
    }
}

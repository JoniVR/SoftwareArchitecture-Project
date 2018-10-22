package be.kdg.processor.service;

import be.kdg.processor.domain.camera.Camera;
import be.kdg.processor.domain.vehicle.Vehicle;
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
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testCameraService() throws IOException, ObjectMappingException {

        Camera camera = proxyService.getCameraObject(2);

        // test if getting object works
        Assert.assertNotNull(camera);
        Assert.assertEquals(2, camera.getId());

        // Test for util to throw correct exception
        thrown.expect(IOException.class);
        thrown.expectMessage("Camera ID 101 forced a communication error for testing purposes");

        proxyService.getCameraObject(101);
    }

    @Test
    public void testLicensePlateService() throws IOException, ObjectMappingException {

        String correctPlate = "1-ABC-123";
        String incorrectPlate = "-ABC-123";

        // Test object returned from service
        Vehicle vehicle = proxyService.getVehicleObject(correctPlate);
        Assert.assertNotNull(vehicle);
        Assert.assertEquals(vehicle.getPlateId(), correctPlate);

        // Test incorrect plate (exception)
        thrown.expect(InvalidLicensePlateException.class);
        thrown.expectMessage("Plate  id "+ incorrectPlate +" is invalid");
        proxyService.getVehicleObject(incorrectPlate);
    }
}

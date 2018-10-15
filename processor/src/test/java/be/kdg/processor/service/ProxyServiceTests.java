package be.kdg.processor.service;

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
    public void testCameraService() throws IOException {

        final String EXPECTEDSTRING = "{\"cameraId\":2,\"location\":{\"lat\":51.229822,\"long\":4.44383}}";

        String testedString = proxyService.cameraServiceProxy().get(2);

        // test if getting regular object matches
        Assert.assertTrue(EXPECTEDSTRING.equals(testedString));

        // Test for mapping to throw correct exception
        thrown.expect(IOException.class);
        thrown.expectMessage("Camera ID 101 forced a communication error for testing purposes");

        proxyService.cameraServiceProxy().get(101);
    }

    @Test
    public void testLicensePlateService() throws IOException {

        String correctPlate = "1-ABC-123";
        String incorrectPlate = "-ABC-123";

        String expectedResponse = "{\"plateId\":\"1-ABC-123\",\"nationalNumber\":\"69.05.22-123.1\",\"euroNumber\":1}";

        // Test correct plate
        String result = proxyService.licensePlateServiceProxy().get(correctPlate);
        Assert.assertEquals(result, expectedResponse);

        // Test incorrect plate (exception)
        thrown.expect(InvalidLicensePlateException.class);
        thrown.expectMessage("Plate  id "+ incorrectPlate +" is invalid");
        proxyService.licensePlateServiceProxy().get(incorrectPlate);
    }
}

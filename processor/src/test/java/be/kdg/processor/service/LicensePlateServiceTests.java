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
public class LicensePlateServiceTests {

    @Autowired
    public ProxyService proxyService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

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

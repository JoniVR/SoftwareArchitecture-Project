package be.kdg.processor.business.violation;

import be.kdg.processor.business.domain.fine.Fine;
import be.kdg.processor.business.domain.settings.Settings;
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

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FineTests {

    @Autowired
    private EmissionViolation emissionViolation;

    @Autowired
    private SpeedingViolation speedingViolation;

    private Settings settings;
    private Violation violation;

    @Before
    public void setUp() {

        violation = new Violation(ViolationType.EMISSION, 100.0, 70, 3, "1-ABC-123,", LocalDateTime.now(), 1,2);
        settings = new Settings();
    }

    @After
    public void tearDown() {

        settings = null;
        violation = null;
    }

    @Test
    public void testEmissionFineCalculation(){

        Fine fine = emissionViolation.calculateFine(violation);

        Assert.assertNotNull("Fine is null.", fine);

        // test amount
        double amount = settings.getEmissionFactor();
        Assert.assertEquals("Fine price is not equal.",fine.getAmount(), amount,0);
    }

    @Test
    public void testSpeedingFineCalculation() {

        Fine fine = speedingViolation.calculateFine(violation);

        Assert.assertNotNull(fine);

        double amount = (violation.getSpeed() - violation.getSpeedLimit()) * settings.getSpeedFactor();
        Assert.assertEquals(amount, fine.getAmount(),0);
    }
}

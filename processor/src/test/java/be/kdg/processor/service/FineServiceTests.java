package be.kdg.processor.service;

import be.kdg.processor.domain.fine.Fine;
import be.kdg.processor.domain.fine.FineType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FineServiceTests {

    @Autowired
    private FineService fineService;

    @Test
    public void testSaveFine() {

        Fine fine = new Fine( 10.0, FineType.EMISSION, true, "Dit is een test.", "1-ABC-123");
        Fine savedFine = fineService.save(fine);
        Assert.assertNotNull("Fine id is null", savedFine.getId());
    }
}

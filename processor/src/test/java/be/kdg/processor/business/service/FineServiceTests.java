package be.kdg.processor.business.service;

import be.kdg.processor.business.domain.camera.Camera;
import be.kdg.processor.business.domain.camera.Location;
import be.kdg.processor.business.domain.camera.Segment;
import be.kdg.processor.business.domain.fine.Fine;
import be.kdg.processor.business.domain.vehicle.Vehicle;
import be.kdg.processor.business.domain.violation.Violation;
import be.kdg.processor.business.domain.violation.ViolationType;
import be.kdg.processor.exceptions.FineException;
import be.kdg.processor.persistence.FineRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FineServiceTests {

    @Mock
    private FineRepository mockFineRepository;

    private FineService fineServiceUnderTest;

    private Camera testCamera;
    private Vehicle vehicle;
    private Fine fineToSave;

    @Before
    public void setUp() {

        initMocks(this);

        fineServiceUnderTest = new FineService(mockFineRepository);

        testCamera = new Camera(1, new Location(),3, new Segment());
        vehicle = new Vehicle("1-ABC-123", "47.11.10-171.40", 1);

        Violation violation = new Violation(ViolationType.EMISSION, null, null, vehicle.getEuroNumber(), vehicle.getPlateId(), LocalDateTime.now(), testCamera.getId(), 2);
        fineToSave = new Fine(1L,10.0, false, null, violation);

        Mockito.when(mockFineRepository.save(fineToSave))
                .thenReturn(fineToSave);

        Mockito.when(mockFineRepository.findById(fineToSave.getId()))
                .thenReturn(Optional.of(fineToSave));
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

        Fine savedFine = fineServiceUnderTest.save(fineToSave);

        Assert.assertNotNull("Fine id is null", savedFine);
    }

    @Test
    @Transactional
    public void testUpdateFineApproved() throws FineException {

        Fine updatedFine = fineServiceUnderTest.updateFineApproved(fineToSave.getId(), true);

        Assert.assertTrue(updatedFine.isApproved());
    }

    @Test
    @Transactional
    public void testUpdateFineAmount() throws FineException {

        String commentText = "Dit is een test";

        Fine updatedFine = fineServiceUnderTest.updateFineAmount(fineToSave.getId(), 200, commentText);

        Assert.assertEquals(200, updatedFine.getAmount(), 0);
        Assert.assertEquals(commentText, updatedFine.getComments());
    }
}

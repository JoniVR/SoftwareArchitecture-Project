package be.kdg.processor.business.service;

import be.kdg.processor.business.domain.settings.Settings;
import be.kdg.processor.persistence.SettingsRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SettingsServiceTests {

    @Mock
    private SettingsRepository settingsRepository;

    private SettingsService settingsServiceUnderTest;

    private Settings settings;

    @Before
    public void setUp() {

        initMocks(this);

        settings = new Settings();
        settings.setId(1L);

        settingsServiceUnderTest = new SettingsService(new ArrayList<>(), settingsRepository);
    }

    @Transactional
    @Test
    public void testLoadSettings() {

        // Setup
        Mockito.when(settingsRepository.findById(settings.getId()))
                .thenReturn(Optional.of(settings));

        // Run the test
        Settings settingsResults = settingsServiceUnderTest.loadSettings();

        // Verify the results
        Assert.assertNotNull(settingsResults);
        Assert.assertEquals(settings.getId(), settingsResults.getId());
    }

    @Transactional
    @Test
    public void testUpdateSettings() {

        // Setup
        Mockito.when(settingsRepository.save(settings))
                .thenReturn(settings);

        // Run the test
        Settings settingsResults = settingsServiceUnderTest.updateSettings(settings);

        // Verify the results
        Assert.assertEquals(settings.getEmissionFactor(), settingsResults.getEmissionFactor(), 0);
    }
}

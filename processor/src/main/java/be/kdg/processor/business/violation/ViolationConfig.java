package be.kdg.processor.business.violation;

import be.kdg.processor.business.domain.camera.ProcessedCameraMessage;
import be.kdg.processor.business.domain.settings.Settings;
import be.kdg.processor.business.service.SettingsService;
import net.jodah.expiringmap.ExpiringMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
public class ViolationConfig {

    @Autowired
    private SettingsService settingsService;

    @Bean
    public EmissionViolation emissionViolation() { return new EmissionViolation(); }

    @Bean
    public SpeedingViolation speedingViolation() { return new SpeedingViolation(); }

    @Bean
    public ExpiringMap<String, List<ProcessedCameraMessage>> speedCameraMessages() {

        Settings settings = settingsService.loadSettings();

        return ExpiringMap
                .builder()
                .variableExpiration()
                .expiration(settings.getSpeedingBufferTimeInMinutes(), TimeUnit.MINUTES)
                .build();
    }
}

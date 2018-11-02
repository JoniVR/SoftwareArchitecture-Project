package be.kdg.processor.business.service;

import be.kdg.processor.business.domain.settings.Settings;
import be.kdg.processor.persistence.SettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class SettingsService {

    @Autowired
    private SettingsRepository settingsRepository;

    public Settings loadSettings() {

        return settingsRepository.findById(1L).orElseGet(this::createSettings);
    }

    public Settings updateSettings(Settings settings) {

        return settingsRepository.save(settings);
    }

    private Settings createSettings() {

        Settings settings = new Settings();

        return settingsRepository.save(settings);
    }
}

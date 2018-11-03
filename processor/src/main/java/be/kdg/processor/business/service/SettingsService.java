package be.kdg.processor.business.service;

import be.kdg.processor.business.domain.settings.Settings;
import be.kdg.processor.business.settings.SettingsListener;
import be.kdg.processor.persistence.SettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
public class SettingsService {

    @Autowired
    private SettingsRepository settingsRepository;
    @Autowired
    private Collection<SettingsListener> settingsListeners;

    public Settings loadSettings() {

        return settingsRepository.findById(1L).orElseGet(this::createSettings);
    }

    public Settings updateSettings(Settings settings) {

        settings.setId(1L);
        notifyListeners(settings);
        return settingsRepository.save(settings);
    }

    private Settings createSettings() {

        Settings settings = new Settings();

        return settingsRepository.save(settings);
    }

    private void notifyListeners(Settings settings){

        for (SettingsListener settingsListener : settingsListeners) {
            settingsListener.onSettingsChanged(settings);
        }
    }
}

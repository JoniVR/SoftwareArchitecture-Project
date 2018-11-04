package be.kdg.processor.business.service;

import be.kdg.processor.business.domain.settings.Settings;
import be.kdg.processor.business.settings.SettingsListener;
import be.kdg.processor.persistence.SettingsRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;

@Service
@Transactional
public class SettingsService {

    private final Collection<SettingsListener> settingsListeners;
    private final SettingsRepository settingsRepository;

    public SettingsService(ArrayList<SettingsListener> settingsListeners, SettingsRepository settingsRepository) {
        this.settingsListeners = settingsListeners;
        this.settingsRepository = settingsRepository;
    }

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

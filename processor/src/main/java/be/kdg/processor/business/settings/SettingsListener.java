package be.kdg.processor.business.settings;

import be.kdg.processor.business.domain.settings.Settings;

/**
 * Implement the SettingsListener to listen for settings changes and notify the correct services.
 */
public interface SettingsListener {

    void onSettingsChanged(Settings settings);
}

package be.kdg.processor.business.settings;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Configuration
public class SettingsConfig {

    @Bean
    public ArrayList<SettingsListener> settingsListeners(){ return new ArrayList<>(); }
}

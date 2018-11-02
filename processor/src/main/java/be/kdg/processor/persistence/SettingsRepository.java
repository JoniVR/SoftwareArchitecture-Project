package be.kdg.processor.persistence;

import be.kdg.processor.business.domain.settings.Settings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettingsRepository extends JpaRepository<Settings, Long> {


}

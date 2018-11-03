package be.kdg.processor.config;

import be.kdg.processor.business.domain.settings.Settings;
import be.kdg.processor.business.service.SettingsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

@Configuration
public class RetryConfig {

    private final SettingsService settingsService;

    public RetryConfig(SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    @Bean
    public RetryTemplate retryTemplate() {

        Settings settings = settingsService.loadSettings();

        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(settings.getRetryMaxAttempts());

        FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
        backOffPolicy.setBackOffPeriod(settings.getRetryBackOffTimeInMs());

        RetryTemplate retryTemplate = new RetryTemplate();
        retryTemplate.setRetryPolicy(retryPolicy);
        retryTemplate.setBackOffPolicy(backOffPolicy);

        return retryTemplate;
    }
}

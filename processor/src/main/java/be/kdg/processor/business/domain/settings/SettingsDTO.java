package be.kdg.processor.business.domain.settings;

import lombok.Data;

@Data
public class SettingsDTO {

    private double emissionFactor;
    private double speedFactor;
    private int emissionTimeFrameInHours;
    private int speedingBufferTimeInMinutes;
    private int retryMaxAttempts;
    private int retryBackOffTimeInMs;
}

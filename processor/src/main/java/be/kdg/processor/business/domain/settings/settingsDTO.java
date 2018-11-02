package be.kdg.processor.business.domain.settings;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class settingsDTO {

    private double emissionFactor;
    private double speedFactor;
    private int emissionTimeFrameInHours;
    private int speedingBufferTimeInMinutes;
    private int retryMaxAttempts;
    private int retryBackOffTimeInMs;
}

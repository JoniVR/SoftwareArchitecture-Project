package be.kdg.processor.business.domain.settings;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
@AllArgsConstructor
public class Settings {

    @Id
    @GeneratedValue
    private Long id;
    private double emissionFactor;
    private double speedFactor;
    private int emissionTimeFrameInHours;
    private int speedingBufferTimeInMinutes;
    private int retryMaxAttempts;
    private int retryBackOffTimeInMs;

    public Settings() {
        this.emissionFactor = 1000;
        this.speedFactor = 1000;
        this.emissionTimeFrameInHours = 24;
        this.speedingBufferTimeInMinutes = 30;
        this.retryMaxAttempts = 3;
        this.retryBackOffTimeInMs = 2000;
    }
}
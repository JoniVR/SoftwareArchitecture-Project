package be.kdg.processor.domain.fine;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
@AllArgsConstructor
public class FineFactor {

    @Id
    @GeneratedValue
    private Long id;
    private double emissionFactor;
    private double speedFactor;
    private int emissionTimeFrameInHours;
    private int speedingBufferTimeInMinutes;

    public FineFactor() {
        this.emissionFactor = 1000;
        this.speedFactor = 1000;
        this.emissionTimeFrameInHours = 24;
        this.speedingBufferTimeInMinutes = 30;
    }
}
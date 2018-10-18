package be.kdg.processor.model.fine;

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

    public FineFactor() {
        this.emissionFactor = 1000;
        this.speedFactor = 1000;
    }
}
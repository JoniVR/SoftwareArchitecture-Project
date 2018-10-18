package be.kdg.processor.model.fine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FineFactorDTO {

    private double emissionFactor;
    private double speedFactor;
}
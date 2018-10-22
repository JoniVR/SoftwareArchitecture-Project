package be.kdg.processor.domain.fine;

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

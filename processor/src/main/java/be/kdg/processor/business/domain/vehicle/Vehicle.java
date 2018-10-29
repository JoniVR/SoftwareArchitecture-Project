package be.kdg.processor.business.domain.vehicle;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class Vehicle {

    @Getter
    @Setter
    private String plateId;
    @Getter
    @Setter
    private String nationalNumber;
    @Getter
    @Setter
    private int euroNumber;

    @Override
    public String toString() {
        return String.format("plate: %s, NN: %s, euronorm: %d", plateId, nationalNumber, euroNumber);
    }
}

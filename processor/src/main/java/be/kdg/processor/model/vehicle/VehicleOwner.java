package be.kdg.processor.model.vehicle;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class VehicleOwner {

    @Getter @Setter
    private String nationaalNummer;
}

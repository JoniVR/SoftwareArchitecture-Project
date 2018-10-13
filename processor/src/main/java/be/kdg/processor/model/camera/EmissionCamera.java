package be.kdg.processor.model.camera;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class EmissionCamera extends Camera {

    @Getter
    @Setter
    private int euroNorm;

    @Override
    public String toString() {
        return String.format("%s, euroNorm: %d", super.toString(), euroNorm);
    }
}

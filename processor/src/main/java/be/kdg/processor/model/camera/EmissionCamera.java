package be.kdg.processor.model.camera;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class EmissionCamera {

    @Getter @Setter
    private int euroNorm;
    @Getter
    private final CameraType cameraType = CameraType.EMISSION;
}

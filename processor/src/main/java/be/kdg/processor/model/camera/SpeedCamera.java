package be.kdg.processor.model.camera;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class SpeedCamera extends Camera {

    @Getter @Setter
    private Segment segment;
    @Getter
    private final CameraType cameraType = CameraType.SPEED;
}

package be.kdg.processor.model.camera;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class Camera {

    @Getter @Setter
    private int id;
    @Getter @Setter
    private Location location;
    @Getter @Setter
    private Segment segment;
    @Getter @Setter
    private int euroNorm;
}

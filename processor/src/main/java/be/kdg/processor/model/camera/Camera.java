package be.kdg.processor.model.camera;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Camera {

    @JsonProperty("cameraId")
    int id;

    private CameraType cameraType;
    private Location location;
    private int euroNorm;
    private Segment segment;

    @Override
    public String toString() {
        return String.format("id: %d, location: %s, euronorm: %d, segment: %s", id, location, euroNorm, segment);
    }
}

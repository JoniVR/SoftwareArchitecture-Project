package be.kdg.processor.business.domain.camera;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Camera {

    @JsonProperty("cameraId")
    int id;

    private Location location;
    private int euroNorm;
    private Segment segment;

    @Override
    public String toString() {
        return String.format("id: %d, location: %s, euronorm: %d, segment: %s", id, location, euroNorm, segment);
    }
}

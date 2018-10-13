package be.kdg.processor.model.camera;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public abstract class Camera {

    @JsonProperty("cameraId")
    @Getter
    @Setter
    int id;

    @Getter
    @Setter
    Location location;

    @Override
    public String toString() {
        return String.format("id: %d, location: %s", id, location);
    }
}

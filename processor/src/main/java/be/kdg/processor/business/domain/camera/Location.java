package be.kdg.processor.business.domain.camera;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Location {

    @JsonProperty("lat")
    private float latitude;

    @JsonProperty("long")
    private float longitude;

    @Override
    public String toString() {
        return String.format("lat: %.5f, long: %.5f", latitude, longitude);
    }
}

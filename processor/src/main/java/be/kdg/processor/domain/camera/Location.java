package be.kdg.processor.domain.camera;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Location {

    @JsonProperty("lat")
    private float lattitude;

    @JsonProperty("long")
    private float longtitude;

    @Override
    public String toString() {
        return String.format("lat: %.5f, long: %.5f", lattitude, longtitude);
    }
}

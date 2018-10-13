package be.kdg.processor.model.camera;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class Location {

    @JsonProperty("lat")
    @Getter
    @Setter
    private float lattitude;

    @JsonProperty("long")
    @Getter
    @Setter
    private float longtitude;

    @Override
    public String toString() {
        return String.format("lat: %.5f, long: %.5f", lattitude, longtitude);
    }
}

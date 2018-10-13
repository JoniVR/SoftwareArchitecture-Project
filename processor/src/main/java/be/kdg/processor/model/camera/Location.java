package be.kdg.processor.model.camera;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class Location {

    @Getter @Setter
    private float lattitude;
    @Getter @Setter
    private float longtitude;
}

package be.kdg.processor.model.camera;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class Segment {

    @Getter
    @Setter
    private int connectedCameraId;

    @Getter
    @Setter
    private long distance;

    @Getter
    @Setter
    private int speedLimit;

    @Override
    public String toString() {
        return String.format("ConnectedCamId: %d, distance: %d, speedLimit: %d", connectedCameraId, distance, speedLimit);
    }
}

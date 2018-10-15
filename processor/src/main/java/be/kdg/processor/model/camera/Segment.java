package be.kdg.processor.model.camera;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Segment {

    private int connectedCameraId;
    private long distance;
    private int speedLimit;

    @Override
    public String toString() {
        return String.format("ConnectedCamId: %d, distance: %d, speedLimit: %d", connectedCameraId, distance, speedLimit);
    }
}

package be.kdg.processor.business.domain.camera;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Segment {

    private long distance;
    private int speedLimit;
    private int connectedCameraId;

    @Override
    public String toString() {
        return String.format("ConnectedCamId: %d, distance: %d, speedLimit: %d", connectedCameraId, distance, speedLimit);
    }
}

package be.kdg.processor.domain.camera;

import be.kdg.processor.domain.vehicle.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ProcessedCameraMessage {

    private Vehicle vehicle;
    private Camera camera;
    private LocalDateTime timeStamp;
}

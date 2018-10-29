package be.kdg.processor.business.domain.camera;

import be.kdg.processor.business.domain.vehicle.Vehicle;
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

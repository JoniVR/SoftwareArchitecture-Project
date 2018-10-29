package be.kdg.processor.business.domain.violation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViolationDTO {

    private ViolationType violationType;
    private Double speed;
    private Integer speedLimit;
    private Integer euroNorm;
    private String licensePlate;
    private LocalDateTime creationDate;
    private Integer cameraId;
    private Integer connectedCameraId;
}

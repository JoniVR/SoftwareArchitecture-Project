package be.kdg.processor.business.domain.violation;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
public class Violation {

    @Id
    @GeneratedValue
    private Long id;

    private ViolationType violationType;
    private Double speed;
    private Integer speedLimit;
    private Integer euroNorm;
    private String licensePlate;
    private LocalDateTime creationDate;
    private Integer cameraId;
    private Integer connectedCameraId;

    public Violation(ViolationType violationType, Double speed, Integer speedLimit, Integer euroNorm, String licensePlate, LocalDateTime creationDate, Integer cameraId, Integer connectedCameraId) {
        this.violationType = violationType;
        this.speed = speed;
        this.speedLimit = speedLimit;
        this.euroNorm = euroNorm;
        this.licensePlate = licensePlate;
        this.creationDate = creationDate;
        this.cameraId = cameraId;
        this.connectedCameraId = connectedCameraId;
    }
}

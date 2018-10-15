package be.kdg.processor.model.camera;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@AllArgsConstructor
public class CameraMessage implements Serializable {

    private int id;
    private String licenseplate;
    private LocalDateTime timestamp;
    private int delay = 1000; //TODO: change default delay once implemented busy hours!!

    public CameraMessage(int id, String licenseplate, LocalDateTime timestamp) {
        this.id = id;
        this.licenseplate = licenseplate;
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CameraMessage that = (CameraMessage) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @Override
    public String toString() {

        return String.format("Camera id: %d, license plate: %s, timestamp: %s", this.id, this.licenseplate, this.timestamp);
    }
}

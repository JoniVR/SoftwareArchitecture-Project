package be.kdg.processor.model.camera;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class CameraMessage implements Serializable {

    private int id;
    private String licenseplate;
    private LocalDateTime timestamp;
    private int delay;

    public CameraMessage() {
        //default, used for serialization
    }

    public CameraMessage(int id, String licenseplate, LocalDateTime timestamp) {
        this.id = id;
        this.licenseplate = licenseplate;
        this.timestamp = timestamp;
        this.delay = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLicenseplate() {
        return licenseplate;
    }

    public void setLicenseplate(String licenseplate) {
        this.licenseplate = licenseplate;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
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

        return String.format("Camera id: %d, license plate: %s, timestamp: %s",this.id,this.licenseplate, this.timestamp);
    }
}

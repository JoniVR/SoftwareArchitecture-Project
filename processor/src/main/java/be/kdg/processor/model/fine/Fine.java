package be.kdg.processor.model.fine;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.h2.util.DateTimeUtils;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
public class Fine {

    @Id
    @GeneratedValue
    private Long id;

    private double amount;
    private FineType fineType;
    private boolean isApproved;
    private String comments;
    private String licensePlate;
    private LocalDateTime creationDate;

    public Fine(double amount, FineType fineType, boolean isApproved, String comments, String licensePlate) {
        this.amount = amount;
        this.fineType = fineType;
        this.isApproved = isApproved;
        this.comments = comments;
        this.licensePlate = licensePlate;
        this.creationDate = LocalDateTime.now();
    }
}

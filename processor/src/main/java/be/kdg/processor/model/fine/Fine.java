package be.kdg.processor.model.fine;

import lombok.*;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Fine {

    @Id
    @GeneratedValue
    private Long id;

    private double amount;
    private FineType fineType;
    private boolean isApproved;
    private String comments;
    private String licensePlate;

    public Fine(double amount, FineType fineType, boolean isApproved, String comments, String licensePlate) {
        this.amount = amount;
        this.fineType = fineType;
        this.isApproved = isApproved;
        this.comments = comments;
        this.licensePlate = licensePlate;
    }
}

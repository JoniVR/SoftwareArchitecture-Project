package be.kdg.processor.business.domain.fine;

import be.kdg.processor.business.domain.violation.Violation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Data
@Entity
@Builder
@AllArgsConstructor
public class Fine {

    @Id
    @GeneratedValue
    private Long id;

    private double amount;
    private boolean isApproved;
    private String comments;

    @OneToOne
    private Violation violation;

    public Fine(double amount, boolean isApproved, String comments, Violation violation) {
        this.amount = amount;
        this.isApproved = isApproved;
        this.comments = comments;
        this.violation = violation;
    }
}

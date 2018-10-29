package be.kdg.processor.business.domain.fine;

import be.kdg.processor.business.domain.violation.Violation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FineDTO {

    private double amount;
    private boolean isApproved;
    private String comments;
    private Violation violation;
}

package be.kdg.processor.domain.fine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FineDTO {

    private double amount;
    private FineType fineType;
    private boolean isApproved;
    private String comments;
    private String licensePlate;
    private LocalDateTime creationDate;
}

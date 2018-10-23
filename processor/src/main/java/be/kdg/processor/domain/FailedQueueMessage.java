package be.kdg.processor.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class FailedQueueMessage {

    @Getter
    @Setter
    private String message;

    @Getter
    @Setter
    private int readAttempt;
}

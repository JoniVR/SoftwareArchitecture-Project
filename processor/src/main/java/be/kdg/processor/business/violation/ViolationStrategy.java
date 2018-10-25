package be.kdg.processor.business.violation;

import be.kdg.processor.domain.camera.ProcessedCameraMessage;
import be.kdg.processor.domain.fine.Fine;

public interface ViolationStrategy {

    boolean detect(ProcessedCameraMessage processedCameraMessage);

    Fine calculateFine(ProcessedCameraMessage processedCameraMessage);
}

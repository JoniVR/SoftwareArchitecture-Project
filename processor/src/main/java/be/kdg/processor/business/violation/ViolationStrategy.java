package be.kdg.processor.business.violation;

import be.kdg.processor.business.domain.camera.ProcessedCameraMessage;
import be.kdg.processor.business.domain.fine.Fine;
import be.kdg.processor.business.domain.violation.Violation;

import java.util.Optional;

/**
 * These are the strategy implementations that are responsible for detecting violations and calculating their respective fines.
 * The storing in the database of these Violations and Fines happens inside the ProcessorMessageHandler.
 */
public interface ViolationStrategy {

    /**
     * This method will detect if a violation has occurred of the specific strategy.
     * If the violation did occur, it will return a Violation of the optional type and the ProcessorMessageHandler
     * will pass this violation to the calculateFine() method.
     * If the violation did not occur, it will return an empty optional.
     * @param processedCameraMessage The CameraMessage that was processed by the Processor.
     * @return An optional Violation, if this violation is not Optional.empty(), the ProcessorMessageHandler
     * will automatically save the fine and call the calculateFine() method below.
     */
    Optional<Violation> detect(ProcessedCameraMessage processedCameraMessage);

    /**
     * This method is called from the ProcessorMessageHandler to handle a detected Violation.
     * It will calculate the fine and return a newly created Fine object.
     * @param violation The violation that is passed from the ProcessorMessageHandler.
     * @return A newly created fine object based on the data from the Violation.
     */
    Fine calculateFine(Violation violation);
}

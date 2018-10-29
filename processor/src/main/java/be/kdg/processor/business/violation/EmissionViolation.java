package be.kdg.processor.business.violation;

import be.kdg.processor.business.domain.camera.Camera;
import be.kdg.processor.business.domain.camera.ProcessedCameraMessage;
import be.kdg.processor.business.domain.fine.Fine;
import be.kdg.processor.business.domain.vehicle.Vehicle;
import be.kdg.processor.business.domain.violation.Violation;
import be.kdg.processor.business.domain.violation.ViolationType;
import be.kdg.processor.business.service.FineFactorService;
import be.kdg.processor.business.service.ViolationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

/**
 * Detects if an emission violation has occurred and calculating the fines.
 */
public class EmissionViolation implements ViolationStrategy {

    @Autowired
    private ViolationService violationService;

    @Autowired
    private FineFactorService fineFactorService;

    @Override
    public Optional<Violation> detect(ProcessedCameraMessage processedCameraMessage) {

        Camera camera = processedCameraMessage.getCamera();
        Vehicle vehicle = processedCameraMessage.getVehicle();

        if (vehicle.getEuroNumber() < camera.getEuroNorm() && camera.getEuroNorm() != 0 && !isDoubleViolation(processedCameraMessage)){

            int connectedCamId = camera.getSegment() == null ? 0 : camera.getSegment().getConnectedCameraId();

            return Optional.of(new Violation(ViolationType.EMISSION, null, null, vehicle.getEuroNumber(), vehicle.getPlateId(), processedCameraMessage.getTimeStamp(), camera.getId(), connectedCamId));
        }
        return Optional.empty();
    }

    public Fine calculateFine(Violation violation) {

        double fineAmount = fineFactorService.loadFineFactor().getEmissionFactor();

        return new Fine(fineAmount, false, null, violation);
    }

    /**
     * Checks if an emission violation has already been detected for this vehicle.
     *
     * @param processedCameraMessage The cameraMessage with all the required specs.
     * @return A boolean value that will return true in case there is already a violation within the set time bounds.
     */
    private boolean isDoubleViolation(ProcessedCameraMessage processedCameraMessage) {

        Camera camera = processedCameraMessage.getCamera();
        Vehicle vehicle = processedCameraMessage.getVehicle();

        int timeFrameInHours = fineFactorService.loadFineFactor().getEmissionTimeFrameInHours();

        Optional<Violation> optionalViolation = violationService.loadLatestViolationFrom(vehicle.getPlateId());

        if (optionalViolation.isPresent()) {
            Violation violation = optionalViolation.get();

            // Check if the previous fine was issued after the allowed timeframe and return correct value
            return violation.getConnectedCameraId() == camera.getId()
                    && violation.getCreationDate().plusHours(timeFrameInHours).isAfter(processedCameraMessage.getTimeStamp());
        }
        return false;
    }
}
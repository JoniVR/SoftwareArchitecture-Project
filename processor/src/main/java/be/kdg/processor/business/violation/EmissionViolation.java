package be.kdg.processor.business.violation;

import be.kdg.processor.domain.camera.Camera;
import be.kdg.processor.domain.camera.ProcessedCameraMessage;
import be.kdg.processor.domain.fine.Fine;
import be.kdg.processor.domain.fine.FineType;
import be.kdg.processor.domain.vehicle.Vehicle;
import be.kdg.processor.service.FineFactorService;
import be.kdg.processor.service.FineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

/**
 * Detects if an emission violation has occurred and calculating the fines.
 */
public class EmissionViolation implements ViolationStrategy {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmissionViolation.class);

    @Autowired
    private FineService fineService;

    @Autowired
    private FineFactorService fineFactorService;

    @Override
    public boolean detect(ProcessedCameraMessage processedCameraMessage) {

        Camera camera = processedCameraMessage.getCamera();
        Vehicle vehicle = processedCameraMessage.getVehicle();

        if (vehicle.getEuroNumber() < camera.getEuroNorm() && camera.getEuroNorm() != 0 && !isDoubleViolation(processedCameraMessage)) {

            LOGGER.info("Detected: emission violation for {}", vehicle);
            return true;
        }
        return false;
    }

    public Fine calculateFine(ProcessedCameraMessage processedCameraMessage) {

        double fineAmount = fineFactorService.loadFineFactor().getEmissionFactor();

        return new Fine(fineAmount, FineType.EMISSION, false, null, processedCameraMessage.getVehicle().getPlateId(), processedCameraMessage.getCamera().getId());
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

        Optional<Fine> optionalFine = fineService.loadLatestFineFrom(vehicle.getPlateId());

        if (optionalFine.isPresent()) {
            Fine fine = optionalFine.get();

            // Check if the previous fine was issued after the allowed timeframe and return correct value
            return fine.getCameraId() == camera.getId()
                    && fine.getCreationDate().plusHours(timeFrameInHours).isAfter(processedCameraMessage.getTimeStamp());
        }
        return false;
    }
}
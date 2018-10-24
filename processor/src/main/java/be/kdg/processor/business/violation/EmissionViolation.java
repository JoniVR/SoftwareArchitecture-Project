package be.kdg.processor.business.violation;

import be.kdg.processor.domain.camera.Camera;
import be.kdg.processor.domain.camera.CameraType;
import be.kdg.processor.domain.fine.Fine;
import be.kdg.processor.domain.fine.FineType;
import be.kdg.processor.domain.vehicle.Vehicle;
import be.kdg.processor.service.FineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Detects if an emission violation has occurred and calculating the fines.
 */
public class EmissionViolation implements ViolationStrategy {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmissionViolation.class);

    @Autowired
    private FineService fineService;

    @Override
    public boolean detect(Camera camera, Vehicle vehicle) {

        if(camera.getCameraType() == CameraType.EMISSION){

            if (vehicle.getEuroNumber() < camera.getEuroNorm() && !isDoubleViolation(camera, vehicle)){

                LOGGER.info("Emission violation detected for {}", vehicle);
                return true;
            }
        }
        return false;
    }

    public Fine calculateFine(Camera camera, Vehicle vehicle){

        double fineAmount = 1000.00;

        return new Fine(fineAmount, FineType.EMISSION, false, null, vehicle.getPlateId(), camera.getId());
    }

    /**
     * Checks if an emission violation has already been detected for this vehicle.
     * @param camera The camera that sended the CameraMessage.
     * @param vehicle The Vehicle that is violating
     * @return A boolean value that will return true in case there is already a violation within the set time bounds.
     */
    private boolean isDoubleViolation(Camera camera, Vehicle vehicle) {

        //TODO: move to FineFactor?
        int timeFrameInHours = 24;

        Optional<Fine> optionalFine = fineService.loadLatestFineFrom(vehicle.getPlateId());

        if (optionalFine.isPresent()){
            Fine fine = optionalFine.get();

            // Check if the previous fine was issued after the allowed timeframe and return correct value
            return fine.getCameraId() == camera.getId()
                    && fine.getCreationDate().plusHours(timeFrameInHours).isAfter(LocalDateTime.now());
        }
        return false;
    }
}
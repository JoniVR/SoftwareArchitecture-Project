package be.kdg.processor.business.violation;

import be.kdg.processor.domain.camera.Camera;
import be.kdg.processor.domain.camera.CameraType;
import be.kdg.processor.domain.fine.Fine;
import be.kdg.processor.domain.fine.FineType;
import be.kdg.processor.domain.vehicle.Vehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Detects if an emission violation has occurred and calculating the fines.
 */
public class EmissionViolation implements ViolationStrategy {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmissionViolation.class);

    @Override
    public boolean detect(Camera camera, Vehicle vehicle) {

        if(camera.getCameraType() == CameraType.EMISSION){

            if (vehicle.getEuroNumber() < camera.getEuroNorm()){

                LOGGER.info("Emission violation detected for {}", vehicle);
                return true;
            }
        }
        return false;
    }

    public Fine calculateFine(Camera camera, Vehicle vehicle){

        double fineAmount = 1000.00;
        Fine fine = new Fine(fineAmount, FineType.EMISSION, false, null, vehicle.getPlateId());

        return fine;
    }
}
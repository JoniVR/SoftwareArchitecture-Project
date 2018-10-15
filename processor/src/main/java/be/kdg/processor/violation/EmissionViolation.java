package be.kdg.processor.violation;

import be.kdg.processor.model.camera.Camera;
import be.kdg.processor.model.camera.CameraType;
import be.kdg.processor.model.fine.Fine;
import be.kdg.processor.model.fine.FineType;
import be.kdg.processor.model.vehicle.Vehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Detects if an emission violation has occured and calculating the fines.
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
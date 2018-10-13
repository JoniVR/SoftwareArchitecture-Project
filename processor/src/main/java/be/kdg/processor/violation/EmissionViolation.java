package be.kdg.processor.violation;

import be.kdg.processor.model.camera.Camera;
import be.kdg.processor.model.camera.CameraType;
import be.kdg.processor.model.fine.Fine;
import be.kdg.processor.model.fine.FineType;
import be.kdg.processor.model.vehicle.Vehicle;
import be.kdg.processor.service.FineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Detects if an emission violation has occured and calls the responsible class to handle this.
 */
public class EmissionViolation implements ViolationStrategy {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmissionViolation.class);

    @Autowired
    private FineService fineService;

    @Override
    public void detect(Camera camera, Vehicle vehicle) {

        if(camera.getCameraType() == CameraType.EMISSION){

            if (vehicle.getEuroNumber() < camera.getEuroNorm()){

                LOGGER.info("Emission violation detected for {}", vehicle);
                calculateFine(vehicle);
            }
        }
    }

    private void calculateFine(Vehicle vehicle){

        double fineAmount = 1000.00;
        Fine fine = new Fine(fineAmount, FineType.EMISSION, false, null, vehicle.getPlateId());

        fineService.save(fine);
        LOGGER.info("Created fine and saved it to the database: {}", fine);
    }
}

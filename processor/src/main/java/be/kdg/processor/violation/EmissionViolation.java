package be.kdg.processor.violation;

import be.kdg.processor.fine.EmissionFine;
import be.kdg.processor.model.camera.Camera;
import be.kdg.processor.model.camera.EmissionCamera;
import be.kdg.processor.model.vehicle.Vehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Detects if an emission violation has occured and calls the responsible class to handle this.
 */
public class EmissionViolation implements ViolationStrategy {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmissionViolation.class);

    @Autowired
    private EmissionFine emissionFine;

    @Override
    public void detect(Camera camera, Vehicle vehicle) {

        if(camera instanceof EmissionCamera){

            EmissionCamera emissionCamera = (EmissionCamera) camera;

            if (vehicle.getEuroNumber() < emissionCamera.getEuroNorm()){

                //TODO: call emissionFine...
                LOGGER.info("Emission violation detected for {}", vehicle);
            }
        }
    }
}

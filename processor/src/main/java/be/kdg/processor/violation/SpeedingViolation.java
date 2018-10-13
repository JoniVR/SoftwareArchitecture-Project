package be.kdg.processor.violation;

import be.kdg.processor.model.camera.Camera;
import be.kdg.processor.model.camera.SpeedCamera;
import be.kdg.processor.model.vehicle.Vehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpeedingViolation implements ViolationStrategy {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpeedingViolation.class);

    @Override
    public void detect(Camera camera, Vehicle vehicle) {

        if(camera instanceof SpeedCamera){

            SpeedCamera speedCamera = (SpeedCamera) camera;

            //TODO: check speed limit.
            /*
            if (){

                //TODO: call speedFine...
                LOGGER.info("Speed violation detected for {}", vehicle);
            }*/
        }
    }
}

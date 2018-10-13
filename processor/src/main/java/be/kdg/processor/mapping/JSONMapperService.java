package be.kdg.processor.mapping;

import be.kdg.processor.exceptions.ObjectMappingException;
import be.kdg.processor.model.camera.Camera;
import be.kdg.processor.model.camera.CameraType;
import be.kdg.processor.model.vehicle.Vehicle;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class JSONMapperService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JSONMapperService.class);

    @Autowired
    private ObjectMapper objectMapper;

    //TODO: better error handling

    public Camera convertJSONStringToCameraObject(String JSONString) throws ObjectMappingException {

        try {

            Camera camera = objectMapper.readValue(JSONString, Camera.class);

            if (camera.getEuroNorm() == 0){
                camera.setCameraType(CameraType.SPEED);
            } else {
                camera.setCameraType(CameraType.EMISSION);
            }

            return camera;

        } catch (IOException e) {

            LOGGER.warn("Error converting JSON String to Camera object.", e);
            throw new ObjectMappingException("Error converting JSON String to Camera object.");
        }
    }

    public Vehicle convertJSONStringToVehicle(String JSONString) throws ObjectMappingException {

        try {

            return objectMapper.readValue(JSONString, Vehicle.class);

        } catch (IOException e) {

            LOGGER.warn("Error converting JSON String to Vehicle object.", e);
            throw new ObjectMappingException("Error converting JSON String to Vehicle object.");
        }
    }
}

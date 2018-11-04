package be.kdg.processor.util;

import be.kdg.processor.business.domain.camera.Camera;
import be.kdg.processor.business.domain.vehicle.Vehicle;
import be.kdg.processor.exceptions.ObjectMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class JSONMapperService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JSONMapperService.class);

    @Autowired
    private ObjectMapper objectMapper;

    public Camera convertJSONStringToCameraObject(String JSONString) throws ObjectMappingException {

        try {

            return objectMapper.readValue(JSONString, Camera.class);

        } catch (IOException e) {

            LOGGER.error("Error converting JSON String to Camera object. Error: {}", e.getMessage());
            throw new ObjectMappingException("Error converting JSON String to Camera object.");
        }
    }

    public Vehicle convertJSONStringToVehicleObject(String JSONString) throws ObjectMappingException {

        try {

            return objectMapper.readValue(JSONString, Vehicle.class);

        } catch (IOException e) {

            throw new ObjectMappingException("Error converting JSON String to Vehicle object.", e);
        }
    }
}

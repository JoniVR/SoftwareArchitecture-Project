package be.kdg.processor.mapping;

import be.kdg.processor.model.camera.Camera;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class JSONMapperService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JSONMapperService.class);

    @Autowired
    private ObjectMapper objectMapper;

    public Camera convertJSONStringToCameraObject(String JSONString) throws IOException {

        return objectMapper.readValue(JSONString, Camera.class);
    }
}

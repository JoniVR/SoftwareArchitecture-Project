package be.kdg.processor.mapping;

import be.kdg.processor.model.camera.CameraMessage;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Responsible for mapping an object to XML (as string).
 */
@Component
public class XMLMapperService {

    private static final Logger LOGGER = LoggerFactory.getLogger(XMLMapperService.class);

    /**
     * Takes a String formatted as XML and returns a CameraMessage object.
     *
     * @return A CameraMessage object.
     */
    public CameraMessage convertXmlStringToObject(String string) {

        CameraMessage cameraMessage = null;

        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.registerModule(new JavaTimeModule());
        xmlMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        try {

            System.out.println(string);
            cameraMessage = xmlMapper.readValue(string, CameraMessage.class);

        } catch (IOException e) {
            LOGGER.warn("An error occured while reading the CameraMessage from the Queue.", e);
        }
        return cameraMessage;
    }
}
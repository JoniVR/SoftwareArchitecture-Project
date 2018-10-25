package be.kdg.processor.util;

import be.kdg.processor.domain.camera.CameraMessage;
import be.kdg.processor.exceptions.ObjectMappingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * Responsible for util an object to XML (as string).
 */
public class XMLMapperService {

    private static final Logger LOGGER = LoggerFactory.getLogger(XMLMapperService.class);

    @Autowired
    private XmlMapper xmlMapper;

    /**
     * Takes a String formatted as XML and returns a CameraMessage object.
     *
     * @return A CameraMessage object.
     */
    public CameraMessage convertXmlStringToCameraMessage(String string) throws ObjectMappingException {

        xmlMapper.registerModule(new JavaTimeModule());
        xmlMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        try {
            return xmlMapper.readValue(string, CameraMessage.class);
        } catch (IOException e) {
            LOGGER.error("Error converting XML String to CameraMessage object. Error: ", e.getMessage());
            throw new ObjectMappingException("Error converting XML String to CameraMessage object.");
        }
    }
}
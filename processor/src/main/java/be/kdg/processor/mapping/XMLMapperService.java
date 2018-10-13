package be.kdg.processor.mapping;

import be.kdg.processor.exceptions.ObjectMappingException;
import be.kdg.processor.model.camera.CameraMessage;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * Responsible for mapping an object to XML (as string).
 */
public class XMLMapperService {

    private static final Logger LOGGER = LoggerFactory.getLogger(XMLMapperService.class);

    @Autowired
    private XmlMapper xmlMapper;

    //TODO: better exception handling

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
            LOGGER.warn("Error converting XML String to CameraMessage object.",e);
            throw new ObjectMappingException("Error converting XML String to CameraMessage object.");
        }
    }
}
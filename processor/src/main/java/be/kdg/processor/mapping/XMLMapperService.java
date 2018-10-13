package be.kdg.processor.mapping;

import be.kdg.processor.model.camera.CameraMessage;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Responsible for mapping an object to XML (as string).
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
    public CameraMessage convertXmlStringToCameraMessage(String string) {

        CameraMessage cameraMessage = null;

        xmlMapper.registerModule(new JavaTimeModule());
        xmlMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        //TODO: handle exception correctly
        try {

            cameraMessage = xmlMapper.readValue(string, CameraMessage.class);

        } catch (IOException e) {
            LOGGER.warn("An error occured while reading the CameraMessage from the Queue.", e);
        }
        return cameraMessage;
    }
}
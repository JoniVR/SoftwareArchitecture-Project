package be.kdg.simulator.util;

import be.kdg.simulator.exceptions.ObjectMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Responsible for util an object to XML (as string).
 */
@Component
public class XMLMapperService {

    /**
     * Takes an object and converts it to a String formatted as XML.
     *
     * @return String formatted as XML.
     */
    public String convertObjectToXml(Object object) throws ObjectMappingException {

        ObjectMapper objectMapper = new XmlMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        try {
            return objectMapper.writeValueAsString(object);
        } catch (IOException e) {
            throw new ObjectMappingException("Error trying to map object to xml.", e);
        }
    }
}

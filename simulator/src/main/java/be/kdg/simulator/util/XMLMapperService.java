package be.kdg.simulator.util;

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
    public String convertObjectToXml(Object object) throws IOException {

        ObjectMapper objectMapper = new XmlMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return objectMapper.writeValueAsString(object);
    }
}

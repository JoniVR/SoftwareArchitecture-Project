package be.kdg.simulator.mapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Responsible for mapping an object to XML (as string).
 */
@Service
public class XMLMapperService {

    private static final Logger LOGGER = LoggerFactory.getLogger(XMLMapperService.class);

    /**
     * Takes an object and converts it to a String formatted as XML.
     * @return String formatted as XML.
     */
    public static String convertObjectToXml(Object object){

        String objectAsXML = null;
        ObjectMapper objectMapper = new XmlMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        JacksonXmlModule xmlModule = new JacksonXmlModule();
        xmlModule.setDefaultUseWrapper(false);

        try {

            objectAsXML = objectMapper.writeValueAsString(object);

        } catch (JsonProcessingException e) {

            LOGGER.warn("Error processing JSON.", e);
        }
        return objectAsXML;
    }
}

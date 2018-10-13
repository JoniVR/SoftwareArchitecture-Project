package be.kdg.processor.mapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public XmlMapper xmlMapper() { return new XmlMapper(); }

    @Bean
    public ObjectMapper objectMapper() { return new ObjectMapper(); }

    @Bean
    public JSONMapperService jsonMapperService() { return new JSONMapperService(); }

    @Bean
    public XMLMapperService xmlMapperService() { return new XMLMapperService(); }
}

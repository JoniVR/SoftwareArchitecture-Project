package be.kdg.simulator.business.generator;

import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Allows for adding custom beans.
 */
@Configuration
public class GeneratorConfig {

    @Value("${filegenerator.path}")
    private String path;

    @Bean
    @ConditionalOnProperty(name = "generator.type", havingValue = "file")
    public MessageGenerator fileGenerator() throws IOException { return new FileGenerator(path); }

    @Bean
    @ConditionalOnProperty(name = "generator.type", havingValue = "random")
    public MessageGenerator randomMessageGenerator() { return new RandomMessageGenerator(); }
}

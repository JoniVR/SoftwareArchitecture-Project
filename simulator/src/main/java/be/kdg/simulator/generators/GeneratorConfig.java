package be.kdg.simulator.generators;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeneratorConfig {

    /**
     * Indien je geen source code beschikbaar hebt kan je via config file ipv @Component.
     * De naam van deze bean is default de naam van deze methode.
     */
    @Bean
    public MessageGenerator fileGenerator(){
        return new FileGenerator();
    }
}

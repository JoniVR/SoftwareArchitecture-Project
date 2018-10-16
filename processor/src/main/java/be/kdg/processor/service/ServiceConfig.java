package be.kdg.processor.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    public ProxyServiceAdapter proxyServiceAdapter() { return new ProxyServiceAdapter(); }
}

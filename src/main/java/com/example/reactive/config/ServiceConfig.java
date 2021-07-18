package com.example.reactive.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ServiceConfig {

    @Bean
    public WebClient localClient(){
        return WebClient.create("http://localhost:8080");
    }
}

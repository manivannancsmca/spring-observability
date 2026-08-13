package com.order_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class AppConfig {

    @Bean
    public RestClient paymentRestClient(RestClient.Builder builder) {
        return builder
                .baseUrl("http://localhost:8082")
                .build();
    }
}
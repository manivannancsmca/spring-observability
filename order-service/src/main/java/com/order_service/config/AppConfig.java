package com.order_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class AppConfig {

    // 1. Explicitly define the RestClient.Builder bean
    @Bean
    public RestClient.Builder restClientBuilder() {
        return RestClient.builder();
    }

    // 2. Inject that builder to create your custom RestClient bean
    @Bean
    public RestClient paymentRestClient(RestClient.Builder builder) {
        return builder
                .baseUrl("http://localhost:8082")
                .build();
    }
}
package com.order_service.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);
    private final RestClient restClient;

    public OrderController(RestClient restClient) {
        //this.restClient = restClientBuilder.baseUrl("http://payment-service:8082").build();
        this.restClient = restClient;
    }

    @GetMapping("/create-order")
    public String createOrder() {
        log.info("Processing order creation in Order Service...");
        
        String paymentResponse = restClient.get()
                .uri("/process-payment")
                .retrieve()
                .body(String.class);

        log.info("Payment response received: {}", paymentResponse);
        return "Order Completed -> " + paymentResponse;
    }
}

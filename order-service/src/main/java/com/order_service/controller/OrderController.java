package com.order_service.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);
    private final RestClient paymentRestClient;

    // Inject the specific RestClient bean created in AppConfig
    public OrderController(RestClient paymentRestClient) {
        this.paymentRestClient = paymentRestClient;
    }

    @GetMapping("/create-order")
    public String createOrder() {
        log.info("Processing order creation...");

        String response = paymentRestClient.get()
                .uri("/process-payment")
                .retrieve()
                .body(String.class);

        log.info("Payment response: {}", response);
        return "Order Completed -> " + response;
    }
}

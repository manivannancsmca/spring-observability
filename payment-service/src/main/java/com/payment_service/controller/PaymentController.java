package com.payment_service.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    private static final Logger log = LoggerFactory.getLogger(PaymentController.class);

    @GetMapping("/process-payment")
    public String processPayment() throws InterruptedException {
        log.info("Processing payment in Payment Service...");
        
        // Simulate processing latency
        Thread.sleep(150);
        
        log.info("Payment successfully processed!");
        return "Payment Approved [TxID: PAY-9921]";
    }
}

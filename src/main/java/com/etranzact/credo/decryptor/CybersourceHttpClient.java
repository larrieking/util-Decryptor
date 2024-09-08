package com.etranzact.credo.decryptor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.PostExchange;

import java.util.Map;

public interface CybersourceHttpClient {


    @PostExchange( value = "/pts/v2/payments")
    ResponseEntity<Map<String, Object>> initiatePayment(@RequestBody CybersourcePaymentRequest request, @RequestHeader Map<String, String> headers);
    @PostMapping("/risk/v1/authentication-setups")
    ResponseEntity<Map<String, Object>> setupPayerAuth(@RequestBody CybersourcePaymentRequest request, @RequestHeader Map<String, Object>headers);

}   

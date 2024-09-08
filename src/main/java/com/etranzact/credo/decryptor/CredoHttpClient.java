package com.etranzact.credo.decryptor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.PostExchange;

import java.util.Map;

public interface CredoHttpClient {

    @PostExchange("/providus/settlement/notification")
    ProvidusResponse notifyProvidus(@RequestBody ProvidusRequest request);

    @PostExchange(value = "/transaction/stanbic/notification", contentType = "application/json")
    StanbicResponse notifyStanbic(@RequestBody Map<String, Object> request);

    @PostExchange( value = "/transaction/interswitch/webhook/notification", contentType = "application/json")
    ResponseEntity<String>notifyUSSD(@RequestBody InterswitchWebhookRequest request, @RequestHeader("X-Interswitch-Signature")String header);




}

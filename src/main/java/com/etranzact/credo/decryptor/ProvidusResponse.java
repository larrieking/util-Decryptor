package com.etranzact.credo.decryptor;

import lombok.Builder;

//{
//        "requestSuccessful": true,
//        "responseCode": "string",
//        "responseMessage": "string",
//        "sessionId": "string"
//        }
@Builder
public record ProvidusResponse(String requestSuccessful,
                               String responseCode,
                               String responseMessage,
                               String sessionId) { }

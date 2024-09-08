package com.etranzact.credo.decryptor;

import lombok.Builder;

import java.util.List;

public record StanbicResponse(
        String responseCode,
        String responseDescription,
        List<ResponseDetail> responseDetails
){};

record ResponseDetail(
        String accountNumber,
        String requestId,
        String responseMessage,
        Boolean isSuccessful){}
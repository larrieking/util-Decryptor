package com.etranzact.credo.decryptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class InterceptorClient implements ClientHttpRequestInterceptor {
    private static final Logger log = LoggerFactory.getLogger(InterceptorClient.class);
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
            log.info("Request: {} {}", request.getMethod(), request.getURI());
            request.getHeaders().add("Authorization ", "Bearer");
            return execution.execute(request, body);
    }
}

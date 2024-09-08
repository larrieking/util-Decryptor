package com.etranzact.credo.decryptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Configuration
public class CredoHttpClientConfig {

    private static final Logger logger = LoggerFactory.getLogger(CredoHttpClientConfig.class);

    @Bean
    CredoHttpClient credoHttpClient() {
        RestClient restClient = RestClient.builder()
                .baseUrl("https://api.credodemo.com")
                .requestInterceptors(c -> c.add(new LoggingInterceptor()))
                .build();

        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient))
                .build();
        return factory.createClient(CredoHttpClient.class);
    }


    @Bean
    CybersourceHttpClient cybersourceHttpClient() {
        RestClient restClient = RestClient.builder()
                .baseUrl("https://apitest.cybersource.com")
                .requestInterceptors(c -> c.add(new LoggingInterceptor()))
                .build();

        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient))
                .build();
        return factory.createClient(CybersourceHttpClient.class);
    }

}

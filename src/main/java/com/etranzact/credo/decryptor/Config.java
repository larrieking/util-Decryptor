//package com.etranzact.credo.decryptor;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestClient;
//import org.springframework.web.client.support.RestClientAdapter;
//import org.springframework.web.service.invoker.HttpServiceProxyFactory;
//
//@Configuration
//public class Config {
//    @Bean
//    CredoHttpClient credoHttpClient() {
//        RestClient restclient = RestClient.builder()
//                .baseUrl("https://api.credodemo.com")
//                .build();
//
//        HttpServiceProxyFactory factory =  HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restclient))
//                .build();
//        return factory.createClient(CredoHttpClient.class);
//
//
//    }
//}

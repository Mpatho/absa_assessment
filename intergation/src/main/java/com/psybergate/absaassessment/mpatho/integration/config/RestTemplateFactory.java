package com.psybergate.absaassessment.mpatho.integration.config;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class RestTemplateFactory {

    @Bean
    public RestTemplate getRestTemplate(@Value("${rest.scheme}") String scheme, @Value("${rest.host}") String host, @Value("${rest.port}") Integer port) {
        RestTemplate restTemplate = new RestTemplate(getRequestFactory());
        DefaultUriBuilderFactory handler = new CustomUriTemplateHandler(scheme, host, port);
        restTemplate.setUriTemplateHandler(handler);
        return restTemplate;
    }

    private HttpClient getHttpClient() {
        return HttpClientBuilder
                .create()
                .build();
    }

    private ClientHttpRequestFactory getRequestFactory() {
        HttpClient httpClient = getHttpClient();
        return new HttpComponentsClientHttpRequestFactory(httpClient);
    }
}

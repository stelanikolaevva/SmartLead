package com.demoprojects.smartlead.common.config;

import com.demoprojects.smartlead.common.client.HuggingFaceClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class HttpClientConfig {

    @Value("${ai.huggingface.token}")
    private String token;

    @Bean
    HuggingFaceClient huggingFaceClient() {
        RestClient restClient = RestClient.builder()
                .baseUrl("https://router.huggingface.co")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .build();

        return HttpServiceProxyFactory
                .builderFor(RestClientAdapter.create(restClient))
                .build()
                .createClient(HuggingFaceClient.class);
    }
}

package com.retrolad.mediatrontelegrambot.config;

import com.retrolad.mediatrontelegrambot.AuthInterceptor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@Data
@RequiredArgsConstructor
public class RestClientConfig {

    private final AuthInterceptor authInterceptor;

    @Value("${mediatron.url}")
    private String apiUrl;

    @Bean
    public RestClient restClient(RestClient.Builder builder) {
        return builder
                .baseUrl(apiUrl)
                .defaultHeader("Accept", "application/json")
                .requestInterceptor(authInterceptor)
                .build();
    }
}

package com.retrolad.mediatrontelegrambot.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class MediatronClientConfig {

    @Value("${REST_CLIENT_ID}")
    private String clientId;

    @Value("${REST_CLIENT_SECRET}")
    private String clientSecret;
}

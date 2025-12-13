package com.retrolad.mediatrontelegrambot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RestClientTokenRequest {

    private String clientId;
    private String clientSecret;
}

package com.retrolad.mediatrontelegrambot.service;

import com.retrolad.mediatrontelegrambot.config.MediatronClientConfig;
import com.retrolad.mediatrontelegrambot.dto.RestClientTokenRequest;
import com.retrolad.mediatrontelegrambot.dto.RestClientTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class MediatronAuthService {

    private final RestClient authRestClient;
    private final MediatronClientConfig mediatronClientConfig;
    private String cachedToken = null;

    public MediatronAuthService(RestClient.Builder builder, MediatronClientConfig mediatronClientConfig,
                                @Value("${mediatron.url}") String baseUrl) {
        this.authRestClient = builder.baseUrl(baseUrl + "/auth/client").build();
        this.mediatronClientConfig = mediatronClientConfig;
    }

    public String getToken() {
        if (cachedToken == null) {
            refreshToken();
        }
        return cachedToken;
    }

    /**
     * Получает jwt из rest-api и кэширует, если он не установлен
     */
    // TODO refreshToken а пока нет
    public void refreshToken() {
        RestClientTokenResponse response = authRestClient.post()
                .uri("/get-token")
                .body(new RestClientTokenRequest(
                        mediatronClientConfig.getClientId(),
                        mediatronClientConfig.getClientSecret()))
                .retrieve()
                .body(RestClientTokenResponse.class);

        if (response != null) {
            cachedToken = response.getAccessToken();
        }
    }
}

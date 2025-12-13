package com.retrolad.mediatrontelegrambot;

import com.retrolad.mediatrontelegrambot.service.MediatronAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements ClientHttpRequestInterceptor {

    private final MediatronAuthService authService;

    // Добавляет токен к каждому запроса, обновляя его если нужно
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        String token = authService.getToken();

        request.getHeaders().add(HttpHeaders.AUTHORIZATION, "Bearer " + token);

        return execution.execute(request, body);
    }
}

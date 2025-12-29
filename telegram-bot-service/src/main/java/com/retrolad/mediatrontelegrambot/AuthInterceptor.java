package com.retrolad.mediatrontelegrambot;

import com.retrolad.mediatrontelegrambot.service.MediatronAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthInterceptor implements ClientHttpRequestInterceptor {

    private final MediatronAuthService authService;

    // Добавляет токен к каждому запросу, обновляя его если нужно
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        long start = System.currentTimeMillis();

        String token = authService.getToken();
        request.getHeaders().add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        ClientHttpResponse response = execution.execute(request, body);

        long duration = System.currentTimeMillis() - start;

        log.info("Вызов внешнего API",
            kv("method", request.getMethod().name()),
            kv("uri", request.getURI().toString()),
            kv("status", response.getStatusCode().value()),
            kv("durationMs", duration)
        );
        return response;
    }
}

package com.retrolad.mediatron.web.filter;

import com.retrolad.mediatron.service.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
@Component
@RequiredArgsConstructor
public class HttpLoggingFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        long start = System.currentTimeMillis();

        log.info("HTTP request received",
                kv("method", request.getMethod()),
                kv("path", request.getRequestURI()));

        String jwtHeader = request.getHeader("Authorization");
        if (jwtHeader != null) {
            String jwt = jwtHeader.replaceAll("Bearer ", "");

            // Проверить кто делал запрос, пользователь или клиентское приложение
            Long userId = jwtService.extractClaim(jwt, claims -> claims.get("userId", Long.class));

            if (userId != null) {
                MDC.put("userId", userId.toString());
            } else {
                String clientId = jwtService.extractClaim(jwt, Claims::getSubject);
                if (clientId == null) {
                    throw new RuntimeException("Запрос от неизвестного источника");
                }
                MDC.put("clientId", clientId);
            }
        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            long duration = System.currentTimeMillis() - start;

            log.info("HTTP request completed",
                    kv("durationMs", duration),
                    kv("method", request.getMethod()),
                    kv("path", request.getRequestURI()),
                    kv("status", response.getStatus()));

            MDC.clear();
        }
    }
}
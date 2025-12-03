package com.retrolad.mediatron.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final SecretKey secretKey = Jwts.SIG.HS256.key().build();

    /**
     * Генерирует токен на основе данных пользователя
     * @param userDetails данные пользователя
     * @return сгенерированный токен
     */
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("authorities", userDetails.getAuthorities())
                .issuedAt(new Date())
                .expiration(java.sql.Date.valueOf(LocalDate.now().plusWeeks(2)))
                .signWith(secretKey)
                .compact();
    }

    /**
     * Проверяет токен на валидность
     * @param token токен
     * @param userDetails данные о
     * @return
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        Claims payload = extractClaims(token);
        String username = payload.getSubject();

        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        return claimResolver.apply(extractClaims(token));
    }

    /**
     * Извлекает все данные из токена, если он валидный
     * @param token токен
     * @return извлеченные данные
     */
    public Claims extractClaims(String token) {
        return Jwts.parser().verifyWith(secretKey).build()
                .parseSignedClaims(token).getPayload();
    }
}

package com.retrolad.mediatron.service;

import com.retrolad.mediatron.security.AuthUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret.key}")
    private String jwtSecretKey;

    /**
     * Генерирует токен на основе данных пользователя
     * @param userDetails данные пользователя
     * @return сгенерированный токен
     */
    public String generateToken(AuthUserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("authorities", userDetails.getAuthorities())
                .issuedAt(new Date())
                .expiration(java.sql.Date.valueOf(LocalDate.now().plusWeeks(2)))
                .claims().add("userId", userDetails.getAuthUser().getId())
                    .and()
                .signWith(getSignedKey())
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
        return Jwts.parser().verifyWith(getSignedKey()).build()
                .parseSignedClaims(token).getPayload();
    }

    private SecretKey getSignedKey() {
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes());
    }
}

package com.retrolad.mediatron.security.filter;

import com.retrolad.mediatron.security.AuthUserDetails;
import com.retrolad.mediatron.security.ClientAuthenticationToken;
import com.retrolad.mediatron.security.ClientValidationService;
import com.retrolad.mediatron.service.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// Гарантируем единоразовый вызов фильтра для одного запроса
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTH_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final ClientValidationService validationService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // Извлекаем токен из запроса
        String authHeader = request.getHeader(AUTH_HEADER);
        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(BEARER_PREFIX.length());
        String type = jwtService.extractClaim(token, claim -> claim.get("type", String.class));

        if ("user".equals(type)) {
            handleUserRequest(token, request);
        } else if ("client".equals(type)) {
            handleClientRequest(token, request);
        } else {
            log.warn("Запрос от неизвестного аутентифицированного источника");
        }

        filterChain.doFilter(request, response);
    }

    private void handleUserRequest(String token, HttpServletRequest request) {
        String username = jwtService.extractClaim(token, Claims::getSubject);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            AuthUserDetails userDetails = (AuthUserDetails) userDetailsService.loadUserByUsername(username);

            // Если токен валидный, создаем контекст с аутентифицированным пользователем.
            // Только для текущего запроса.
            if (jwtService.isTokenValid(token, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                // Добавляем дополнительные данные, такие как ip, id сессии и т.д
                authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
    }


    private void handleClientRequest(String token, HttpServletRequest request) {
        String clientId = jwtService.extractClaim(token, Claims::getSubject);

        if (!validationService.isClientTrusted(clientId)) return;
        ClientAuthenticationToken authentication = new ClientAuthenticationToken(
                clientId,
                AuthorityUtils.createAuthorityList("ROLE_CLIENT"));

        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}

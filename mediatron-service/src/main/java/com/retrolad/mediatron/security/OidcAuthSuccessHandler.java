package com.retrolad.mediatron.security;

import com.retrolad.mediatron.service.AuthUserDetailsService;
import com.retrolad.mediatron.service.JwtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
@Component
@RequiredArgsConstructor
public class OidcAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final AuthUserDetailsService userDetailsService;
    private final JwtService jwtService;

    @Value("${app.front.url}")
    private String frontUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        OidcUser principal = (OidcUser) authentication.getPrincipal();
        String email = principal.getEmail();
        AuthUserDetails userDetails = (AuthUserDetails) userDetailsService.loadUserByUsername(email);

        log.info("Вход пользователя через систему OAuth2",
                kv("userId", userDetails.getAuthUser().getId()));

        String token = jwtService.generateToken(userDetails);

        String targetUrl = UriComponentsBuilder.fromUriString(frontUrl + "/oauth-success")
                .queryParam("accessToken", token)
                .build().toUriString();

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}

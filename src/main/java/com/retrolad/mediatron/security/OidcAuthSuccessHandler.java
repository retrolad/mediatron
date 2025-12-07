package com.retrolad.mediatron.security;

import com.retrolad.mediatron.service.AuthUserDetailsService;
import com.retrolad.mediatron.service.JwtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OidcAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final AuthUserDetailsService userDetailsService;
    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OidcUser principal = (OidcUser) authentication.getPrincipal();
        String email = principal.getEmail();
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        String token = jwtService.generateToken(userDetails);
        response.sendRedirect("http://localhost:5173/oauth-success?accessToken=" + token);
    }
}

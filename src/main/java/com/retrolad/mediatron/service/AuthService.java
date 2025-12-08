package com.retrolad.mediatron.service;

import com.retrolad.mediatron.dto.JwtAuthResponse;
import com.retrolad.mediatron.dto.SignInRequest;
import com.retrolad.mediatron.security.AuthUserDetails;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthUserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Аутентификация пользователя
     * @param request данные для аутентификации
     * @return токен
     */
    public JwtAuthResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());

        String token = jwtService.generateToken((AuthUserDetails) userDetails);
        return new JwtAuthResponse(token);
    }
}

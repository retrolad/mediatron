package com.retrolad.mediatron.service;

import com.retrolad.mediatron.dto.JwtAuthResponse;
import com.retrolad.mediatron.dto.SignInRequest;
import com.retrolad.mediatron.repository.AuthUserRepository;
import com.retrolad.mediatron.security.AuthUser;
import com.retrolad.mediatron.security.AuthUserDetails;
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
    private final AuthUserRepository authUserRepository;

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

    public AuthUser getByTelegramId(Long telegramId) {
        return authUserRepository.findByTelegramId(telegramId).orElse(null);
    }
}

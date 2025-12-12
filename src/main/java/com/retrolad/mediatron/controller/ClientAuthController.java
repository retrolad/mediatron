package com.retrolad.mediatron.controller;

import com.retrolad.mediatron.dto.JwtAuthResponse;
import com.retrolad.mediatron.dto.telegram.TelegramClientSignInRequest;
import com.retrolad.mediatron.dto.UserProfileDto;
import com.retrolad.mediatron.security.TrustedClientsProperties;
import com.retrolad.mediatron.service.ClientAuthService;
import com.retrolad.mediatron.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/client")
@RequiredArgsConstructor
public class ClientAuthController {

    private final ClientAuthService authService;
    private final JwtService jwtService;

    @PostMapping("/get-token")
    public JwtAuthResponse getToken(@RequestBody TrustedClientsProperties.ClientConfig client) {
        String token = jwtService.generateClientToken(client);
        return new JwtAuthResponse(token);
    }

    @PostMapping("/telegram/create-user")
    public ResponseEntity<?> createUser(@RequestBody TelegramClientSignInRequest request) {
        UserProfileDto profile = authService.telegramCreateUser(request);
        if (profile == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.ok(profile);
    }
}

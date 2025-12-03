package com.retrolad.mediatron.controller;

import com.retrolad.mediatron.dto.JwtAuthResponse;
import com.retrolad.mediatron.dto.SignInRequest;
import com.retrolad.mediatron.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-in")
    public JwtAuthResponse signIn(@RequestBody SignInRequest request) {
        return authService.signIn(request);
    }
}

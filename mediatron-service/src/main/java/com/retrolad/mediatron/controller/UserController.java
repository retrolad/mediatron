package com.retrolad.mediatron.controller;

import com.retrolad.mediatron.dto.UserProfileDto;
import com.retrolad.mediatron.security.AuthUserDetails;
import com.retrolad.mediatron.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/api/profile")
    public UserProfileDto getProfile(@AuthenticationPrincipal AuthUserDetails userDetails) {
        return userService.getUserProfile(userDetails.getAuthUser().getId());
    }
}

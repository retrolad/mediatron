package com.retrolad.mediatron.controller;

import com.retrolad.mediatron.dto.UserProfileDto;
import com.retrolad.mediatron.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/client")
@RequiredArgsConstructor
public class ClientApiController {

    private final UserService userService;

    @GetMapping("/users/by-telegram-id/{telegramId}")
    public ResponseEntity<?> getUserByTelegramId(@PathVariable Long telegramId) {
        UserProfileDto dto = userService.getUserByTelegramId(telegramId);
        if (dto == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }
}

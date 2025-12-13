package com.retrolad.mediatron.controller;

import com.retrolad.mediatron.dto.UserMovieDto;
import com.retrolad.mediatron.dto.UserProfileDto;
import com.retrolad.mediatron.service.UserMovieService;
import com.retrolad.mediatron.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/client")
@RequiredArgsConstructor
public class ClientApiController {

    private final UserService userService;
    private final UserMovieService userMovieService;

    @GetMapping("/users/by-telegram-id/{telegramId}")
    public ResponseEntity<?> getUserByTelegramId(@PathVariable Long telegramId) {
        UserProfileDto dto = userService.getUserByTelegramId(telegramId);
        if (dto == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/users/{id}/watchlist")
    public List<UserMovieDto> getUserWatchlist(@PathVariable Long id, @RequestParam String lang) {
        return userMovieService.getUserWatchlist(id, lang);
    }

    @PostMapping("/users/{id}/watchlist/add")
    public ResponseEntity<?> addMovieToWatchlist(@PathVariable Long id, @RequestParam Long movieId) {
        userMovieService.addMovieToUserWatchlist(movieId, id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/users/{id}/watchlist/delete")
    public ResponseEntity<?> removeFromWatchlist(@PathVariable Long id, @RequestParam Long movieId) {
        userMovieService.removeMovieFromWatchlist(movieId, id);
        return ResponseEntity.ok().build();
    }
}

package com.retrolad.mediatron.controller;

import com.retrolad.mediatron.dto.ImageSize;
import com.retrolad.mediatron.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/movies")
public class MovieController {

    private MovieService movieService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getMovie(@PathVariable Long id, @RequestParam String lang) {
        return ResponseEntity.ok(movieService.getById(id, lang, ImageSize.FULL));
    }
}

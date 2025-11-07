package com.retrolad.mediatron.controller;

import com.retrolad.mediatron.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/movies")
public class MovieController {

    private MovieService movieService;

    @GetMapping
    public ResponseEntity<?> getMovies(@RequestParam Integer year) {
        return ResponseEntity.ok(movieService.getByYear(year));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMovie(@PathVariable Long id, @RequestParam String lang) {
        return ResponseEntity.ok(movieService.getById(id, lang));
    }
}

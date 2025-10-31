package com.retrolad.mediatron.controller;

import com.retrolad.mediatron.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/movies")
public class MovieController {

    private MovieService movieService;

    @GetMapping
    public ResponseEntity<?> getMovies(@RequestParam Integer year) {
        return ResponseEntity.ok(movieService.getByYear(year));
    }


}

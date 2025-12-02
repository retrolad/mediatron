package com.retrolad.mediatron.controller;

import com.retrolad.mediatron.dto.ImageSize;
import com.retrolad.mediatron.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/movies")
public class MovieRestController {

    private MovieService movieService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getMovie(@PathVariable Long id, @RequestParam(required = false) String lang) {
        return ResponseEntity.ok(movieService.getById(id, lang, ImageSize.FULL));
    }

    @GetMapping("/cards")
    public ResponseEntity<?> getCards(@PageableDefault(size = 10, sort = "createdAt,desc") Pageable pageable) {
        return ResponseEntity.ok(movieService.getMovieCards(pageable));
    }

    @GetMapping("/hero")
    public ResponseEntity<?> getHero(@PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC)
                                         Pageable pageable) {
        return ResponseEntity.ok(movieService.getMovieHero(pageable));
    }
}

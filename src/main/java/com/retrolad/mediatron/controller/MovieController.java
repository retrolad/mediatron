package com.retrolad.mediatron.controller;

import com.retrolad.mediatron.dto.MovieTitleQueryDto;
import com.retrolad.mediatron.utils.images.ImageSize;
import com.retrolad.mediatron.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/movies")
public class MovieController {

    private MovieService movieService;

    // TODO Избавиться от передачи локалей в каждом методе контроллеров и сервисов
    @GetMapping("/{id}")
    public ResponseEntity<?> getMovie(@PathVariable Long id, @RequestParam(required = false) String lang) {
        return ResponseEntity.ok(movieService.getFullDtoById(id, lang, ImageSize.FULL));
    }

    @GetMapping("/cards")
    public ResponseEntity<?> getCards(@PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
                                      @RequestParam(required = false) String lang) {
        return ResponseEntity.ok(movieService.getMovieCards(pageable, lang));
    }

    @GetMapping("/hero")
    public ResponseEntity<?> getHero(@PageableDefault(size = 5, sort = "year", direction = Sort.Direction.DESC)
                                     Pageable pageable, @RequestParam(required = false) String lang) {
        return ResponseEntity.ok(movieService.getMovieHero(pageable, lang));
    }

    @GetMapping("/years")
    public ResponseEntity<?> getYears() {
        return ResponseEntity.ok(movieService.getAllYears());
    }

    @GetMapping("/years/{year}")
    public ResponseEntity<?> getByYear(@PathVariable Integer year, @RequestParam(required = false) String lang) {
        return ResponseEntity.ok(movieService.getByYear(year, ImageSize.FULL, lang));
    }

    @GetMapping("/titles")
    public List<MovieTitleQueryDto> getTitlesByQuery(@RequestParam String query) {
        if (query.length() < 3) return List.of();
        return movieService.getTitlesByQuery(query);
    }

    @GetMapping("/search")
    public List<String> getByQuery(@RequestParam String query, Pageable pageable) {
        return List.of("Blade Runner");
    }
}

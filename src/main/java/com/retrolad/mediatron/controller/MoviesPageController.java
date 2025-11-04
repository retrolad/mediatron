package com.retrolad.mediatron.controller;

import com.retrolad.mediatron.dto.MovieDto;
import com.retrolad.mediatron.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping
@AllArgsConstructor
public class MoviesPageController {

    private MovieService movieService;

    @GetMapping("/")
    public String getMoviesPage(Model model) {
        model.addAttribute("years", movieService.getAll().stream()
                .map(MovieDto::year)
                .sorted()
                .distinct()
                .toList());
        return "index";
    }

    @GetMapping("/movies-list")
    public String getMoviesByYearPage(@RequestParam(name = "year") Integer year, Model model) {
        model.addAttribute("movies", movieService.getByYear(year));
        model.addAttribute("year", year);
        return "movies";
    }
}

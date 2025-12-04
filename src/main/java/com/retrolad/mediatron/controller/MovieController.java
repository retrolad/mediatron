package com.retrolad.mediatron.controller;

import com.retrolad.mediatron.utils.images.ImageSize;
import com.retrolad.mediatron.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Comparator;

@Controller
@RequestMapping
@AllArgsConstructor
public class MovieController {

    private MovieService movieService;

    @GetMapping
    public String getIndex(Model model, @PageableDefault(size = 5, sort = "year", direction = Sort.Direction.DESC)
            Pageable heroPageable, @RequestParam(required = false) String lang) {

        model.addAttribute("movieHero", movieService.getMovieHero(heroPageable, lang));
        return "index";
    }

    @GetMapping("/carousel")
    public String getMoviesPage(Model model, @RequestParam(required = false) String lang) {
        model.addAttribute("years", movieService.getAllYears()
                .stream().sorted(Comparator.reverseOrder()));
        model.addAttribute("lang", lang);
        return "carousel";
    }

    @GetMapping("/movies-list")
    public String getMoviesByYearPage(@RequestParam(name = "year") Integer year, @RequestParam(required = false) String lang, Model model) {
        model.addAttribute("movies", movieService.getByYear(year, ImageSize.FULL, lang));
        model.addAttribute("year", year);
        model.addAttribute("lang", lang);
        return "movies";
    }

    @GetMapping("/movie/{id}")
    public String getMovieProfilePage(@PathVariable Long id, Model model, @RequestParam(required = false) String lang) {
        model.addAttribute("movie", movieService.getById(id, lang, ImageSize.FULL));
        return "movie-page";
    }
}

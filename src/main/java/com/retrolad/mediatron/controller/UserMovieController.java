package com.retrolad.mediatron.controller;

import com.retrolad.mediatron.dto.MovieCardDto;
import com.retrolad.mediatron.dto.UserMovieDto;
import com.retrolad.mediatron.dto.UserMovieRequest;
import com.retrolad.mediatron.service.UserMovieService;
import com.retrolad.mediatron.utils.images.ImageSize;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserMovieController {

    private final UserMovieService userMovieService;

    @GetMapping("/user/movies")
    public List<UserMovieDto> getUserMovies(UserMovieRequest request) {
        return userMovieService.getUserMovies(request);
    }
}

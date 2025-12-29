package com.retrolad.mediatron.controller;

import com.retrolad.mediatron.dto.MovieIdDto;
import com.retrolad.mediatron.dto.UserMovieDto;
import com.retrolad.mediatron.dto.UserMovieRequest;
import com.retrolad.mediatron.model.user.UserMovieRelation;
import com.retrolad.mediatron.security.AuthUserDetails;
import com.retrolad.mediatron.service.MovieService;
import com.retrolad.mediatron.service.UserMovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserMovieController {

    private final UserMovieService userMovieService;
    private final MovieService movieService;

    @GetMapping("/user/movies")
    public List<UserMovieDto> getUserMovies(UserMovieRequest request) {
        return userMovieService.getUserMovies(request);
    }

    @PostMapping("/user/watchlist")
    public ResponseEntity<?> addMovieToWatchlist(@RequestBody MovieIdDto movieIdDto,
                                                 @AuthenticationPrincipal AuthUserDetails userDetails) {
        userMovieService.addMovieToUserWatchlist(movieIdDto.movieId(), userDetails.getAuthUser().getId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/user/watchlist")
    public ResponseEntity<?> removeMovieFromWatchlist(@RequestBody MovieIdDto movieIdDto,
                                                      @AuthenticationPrincipal AuthUserDetails userDetails) {
        userMovieService.removeMovieFromWatchlist(movieIdDto.movieId(), userDetails.getAuthUser().getId());
        return ResponseEntity.ok().build();
    }
}

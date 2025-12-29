package com.retrolad.mediatron.service;

import com.retrolad.mediatron.dto.MovieTitleQueryDto;
import com.retrolad.mediatron.dto.UserMovieDto;
import com.retrolad.mediatron.dto.UserMovieRequest;
import com.retrolad.mediatron.mapper.MovieMapper;
import com.retrolad.mediatron.mapper.UserMovieMapper;
import com.retrolad.mediatron.model.movie.Movie;
import com.retrolad.mediatron.model.user.User;
import com.retrolad.mediatron.model.user.UserMovieRelation;
import com.retrolad.mediatron.model.user.UserMovieRelationId;
import com.retrolad.mediatron.repository.MovieRepository;
import com.retrolad.mediatron.repository.UserMovieRepository;
import com.retrolad.mediatron.utils.images.ImageSize;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserMovieService {

    private final UserMovieRepository userMovieRepository;
    private final UserService userService;
    private final MovieService movieService;
    private final UserMovieMapper userMovieMapper;

    public List<UserMovieDto> getUserMovies(UserMovieRequest request) {
        Page<UserMovieRelation> relations = userMovieRepository.findAll((
                root,
                query,
                builder) -> {

            List<Predicate> predicates = new ArrayList<>();

            predicates.add(builder.equal(root.get("user").get("id"), request.userId()));

            if (request.isWatched() != null) {
                predicates.add(builder.equal(root.get("isWatched"), request.isWatched()));
            }

            if (request.isInWatchlist() != null) {
                predicates.add(builder.equal(root.get("isInWatchlist"), request.isInWatchlist()));
            }

            if (request.isFavourite() != null) {
                predicates.add(builder.equal(root.get("isFavourite"), request.isFavourite()));
            }

            if (request.isRated() != null) {
                predicates.add(builder.equal(root.get("isRated"), request.isRated()));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        }, PageRequest.of(request.page(), request.size()));

        return relations.stream()
                .map(m -> userMovieMapper.toDto(m, request.lang()))
                .toList();
    }

    public List<UserMovieDto> getUserWatchlist(Long userId, String lang) {
        userService.getUserById(userId);
        return userMovieRepository.findByUserIdAndIsInWatchlist(userId, true).stream()
                .map(umr -> userMovieMapper.toDto(umr, lang))
                .toList();
    }

    /**
     * Добавляет фильм в список фильмов пользователя, с которыми он
     * взаимодействовал
     * @param movieId Идентификатор фильма
     * @param userId Идентификатор пользователя
     */
    public UserMovieRelation addMovieToUserWatchlist(Long movieId, Long userId) {
        User user = userService.getUserById(userId);
        Movie movie = movieService.getById(movieId);

        UserMovieRelation umr = new UserMovieRelation();
        umr.setId(new UserMovieRelationId(userId, movieId));

        umr.setMovie(movie);
        umr.setUser(user);

        umr.setIsInWatchlist(true);

        UserMovieRelation saved = userMovieRepository.save(umr);

        log.info("Фильм добавлен в `watchlist` пользователя",
                kv("movieId", movieId));
        return saved;
    }

    public void removeMovieFromWatchlist(Long movieId, Long userId) {
        User user = userService.getUserById(userId);
        Movie movie = movieService.getById(movieId);

        userMovieRepository.deleteById(new UserMovieRelationId(user.getId(), movie.getId()));

        log.info("Фильм удален из списка `watchlist` пользователя",
                kv("movieId", movieId));
    }
}

package com.retrolad.mediatron.service;

import com.retrolad.mediatron.dto.UserMovieDto;
import com.retrolad.mediatron.dto.UserMovieRequest;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserMovieService {

    private final UserMovieRepository userMovieRepository;
    private final UserService userService;
    private final MovieService movieService;
    private final UserMovieMapper userMovieMapper;

    public List<UserMovieDto> getUserMovies(UserMovieRequest request) {
        Page<UserMovieRelation> relations = userMovieRepository.findAll((root, query, builder) -> {
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

    /**
     * Добавляет фильм в список фильмов пользователя, с которыми он
     * взаимодействовал
     * @param movieId Идентификатор фильма
     * @param userId Идентификатор пользователя
     */
    public UserMovieRelation addMovieToUserWatchlist(Long movieId, Long userId) {
        User user = userService.getUserById(userId);
        Movie movie = movieService.getById(movieId);

        UserMovieRelation usr = new UserMovieRelation();
        usr.setId(new UserMovieRelationId(userId, movieId));

        usr.setMovie(movie);
        usr.setUser(user);

        usr.setIsInWatchlist(true);

        return userMovieRepository.save(usr);
    }

    public void removeMovieFromWatchlist(Long movieId, Long userId) {
        User user = userService.getUserById(userId);
        Movie movie = movieService.getById(movieId);

        userMovieRepository.deleteById(new UserMovieRelationId(user.getId(), movie.getId()));
    }
}

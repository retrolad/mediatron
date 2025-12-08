package com.retrolad.mediatron.service;

import com.retrolad.mediatron.dto.UserMovieDto;
import com.retrolad.mediatron.dto.UserMovieRequest;
import com.retrolad.mediatron.mapper.UserMovieMapper;
import com.retrolad.mediatron.model.user.UserMovieRelation;
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
}

package com.retrolad.mediatron.repository;

import com.retrolad.mediatron.dto.MovieTitleQueryDto;
import com.retrolad.mediatron.model.movie.Movie;
import com.retrolad.mediatron.model.user.User;
import com.retrolad.mediatron.model.user.UserMovieRelation;
import com.retrolad.mediatron.model.user.UserMovieRelationId;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMovieRepository extends JpaRepository<UserMovieRelation, UserMovieRelationId>,
        JpaSpecificationExecutor<UserMovieRelation> {

    List<UserMovieRelation> findByUserIdAndIsInWatchlist(Long userId, boolean isInWatchlist);
}

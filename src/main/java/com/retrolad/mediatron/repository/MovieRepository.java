package com.retrolad.mediatron.repository;

import com.retrolad.mediatron.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query("""
    SELECT DISTINCT m.year FROM Movie m
    """)
    List<Integer> findAllYears();

    @Query("""
    SELECT DISTINCT m FROM Movie m
    LEFT JOIN MovieTranslation t
    ON m.id = t.id.movieId
    WHERE t.langCode = :lang AND m.year = :year
    """)
    List<Movie> findByYearWithTranslation(@Param("year") int year, @Param("lang") String lang);

    @Query("""
    SELECT m FROM Movie m
    LEFT JOIN MovieTranslation t
    ON m.id = t.id.movieId
    WHERE t.langCode = :lang and m.id = :id
    """)
    Optional<Movie> findByIdWithTranslation(@Param("id") Long id, @Param("lang") String lang);
}

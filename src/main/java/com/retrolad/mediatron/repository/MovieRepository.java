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
    LEFT JOIN FETCH m.translations t
    LEFT JOIN FETCH m.genres g
    LEFT JOIN FETCH m.productionCountries pc
    LEFT JOIN FETCH m.ratings r
    LEFT JOIN FETCH m.votes v
    LEFT JOIN FETCH m.externalIds e
    LEFT JOIN FETCH m.images i
    WHERE m.year = :year and t.langCode = :lang
    """)
    List<Movie> findByYearWithTranslation(@Param("year") int year, @Param("lang") String lang);

    @Query("""
    SELECT DISTINCT m FROM Movie m
    JOIN FETCH m.translations t
    JOIN FETCH m.genres g
    JOIN FETCH m.productionCountries pc
    JOIN FETCH m.ratings r
    JOIN FETCH m.votes v
    JOIN FETCH m.externalIds e
    WHERE t.langCode = :lang and m.id = :id
    """)
    Optional<Movie> findByIdWithTranslation(@Param("id") Long id, @Param("lang") String lang);
}

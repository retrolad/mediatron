package com.retrolad.mediatron.repository;

import com.retrolad.mediatron.model.movie.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
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
        SELECT DISTINCT m
        FROM Movie m
        JOIN m.translations t
        WHERE m.year = :year and t.langCode = :lang
    """)
    List<Movie> findByYearWithTranslation(@Param("year") int year, @Param("lang") String lang);

    @Query("""
        SELECT DISTINCT m
        FROM Movie m
        JOIN m.translations t
        WHERE t.langCode = :lang
    """)
    List<Movie> findWithTranslation(String lang, Pageable pageable);

    @Query("""
        SELECT DISTINCT m
        FROM Movie m
        JOIN m.translations t
        WHERE t.langCode = :lang and m.id = :id
    """)
    Optional<Movie> findByIdWithTranslation(@Param("id") Long id, @Param("lang") String lang);

    @Query("""
        SELECT DISTINCT m
        FROM Movie m
        JOIN m.translations mt on mt.langCode = :lang
    """)
    Page<Movie> findMovieCards(String lang, Pageable pageable);

    @Query("""
        SELECT m.id
        FROM Movie m
        JOIN m.translations t
            on t.langCode = :lang
    """)
    List<Long> findMovieIdsByLang(String lang, Pageable pageable);

    @EntityGraph(attributePaths = {
        "translations",
        "images",
        "images.type",
        "genres"
    })
    @Query("""
        SELECT m
        FROM Movie m
        WHERE m.id in :ids
    """)
    List<Movie> findMovieByIds(List<Long> ids);
}

package com.retrolad.mediatron.repository;

import com.retrolad.mediatron.model.movie.MovieTranslation;
import com.retrolad.mediatron.model.movie.MovieTranslationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieTranslationRepository extends JpaRepository<MovieTranslation, MovieTranslationId> {

    @Query(
    """
    SELECT t
    FROM MovieTranslation t
    WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', :title, '%'))
    AND (t.langCode = :userLang OR t.langCode = 'en')
    """)
    List<MovieTranslation> findAllByTitle(String title, String userLang);
}

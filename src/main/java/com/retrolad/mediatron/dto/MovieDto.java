package com.retrolad.mediatron.dto;

import java.util.List;
import java.util.Map;

public record MovieDto(
        Long id,
        String originalTitle,
        int year,
        int runtime,
        String title,
        String description,
        String tagline,
        String ratingMpaa,
        Long budget,
        Short ageRating,
        List<GenreDto> genres,
        List<String> countries,
        Map<String, Float> ratings,
        Map<String, Integer> votes,
        Map<String, String> externalIds,
        ImageDto images,
        List<MoviePersonDto> cast,
        Map<String, List<MoviePersonDto>> crew) {
}

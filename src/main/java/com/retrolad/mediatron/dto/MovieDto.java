package com.retrolad.mediatron.dto;

import java.util.List;

public record MovieDto(Long id, String originalTitle, int year, int runtime, String title, String description,
                       String tagline, String ratingMpaa, Short ageRating, List<String> genres, List<String> countries) {
}

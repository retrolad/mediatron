package com.retrolad.mediatron.dto;

import java.util.List;

public record MovieHeroDto(Long id, int year, String title, String originalTitle, String description, String runtime, Short ageRating,
                           List<String> genres,
                           ImageDto images) {
}

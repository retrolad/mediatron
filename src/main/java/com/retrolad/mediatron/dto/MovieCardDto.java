package com.retrolad.mediatron.dto;

public record MovieCardDto(Long id, String title, String posterUrl, int year, Float imdbRating, Float kpRating) {
}

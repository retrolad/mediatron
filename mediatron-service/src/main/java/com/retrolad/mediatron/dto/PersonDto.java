package com.retrolad.mediatron.dto;

import java.time.LocalDate;

public record PersonDto(
        Long id,
        String name,
        String originalName,
        String biography,
        String placeOfBirth,
        LocalDate birthday,
        LocalDate deathday,
        String imageUrl) {
}

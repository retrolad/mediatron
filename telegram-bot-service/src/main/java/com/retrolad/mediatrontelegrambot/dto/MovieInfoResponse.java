package com.retrolad.mediatrontelegrambot.dto;

public record MovieInfoResponse (
        Long id, String title,
        String posterUrl,
        int year,
        Float imdbRating,
        Float kpRating) { }

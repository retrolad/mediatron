package com.retrolad.mediatron.dto;

public record UserMovieRequest (
        Long userId,
        Boolean isWatched,
        Boolean isInWatchlist,
        Boolean isFavourite,
        Boolean isRated,
        int page,
        int size,
        String lang
) { }

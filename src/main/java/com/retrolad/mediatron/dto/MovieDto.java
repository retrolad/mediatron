package com.retrolad.mediatron.dto;

import java.util.List;

public record MovieDto (Long id, String title, String originalTitle, int releaseYear, String description,
                        String director, short duration, List<String> genres) {}

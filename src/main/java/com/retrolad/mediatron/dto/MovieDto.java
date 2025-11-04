package com.retrolad.mediatron.dto;

import java.util.List;

public record MovieDto (int id, String originalTitle, int year, int runtime, String ratingMpaa, Short ageRating) {}

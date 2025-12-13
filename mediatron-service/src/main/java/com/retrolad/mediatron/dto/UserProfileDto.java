package com.retrolad.mediatron.dto;

public record UserProfileDto(
        Long id,
        String email,
        String username
) {
}

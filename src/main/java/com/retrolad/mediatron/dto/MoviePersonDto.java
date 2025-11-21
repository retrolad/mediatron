package com.retrolad.mediatron.dto;

/**
 * Данные о персоне из съемочного состава фильма
 */
public record MoviePersonDto(Long personId, String name, String characterName, String role, Short order) { }

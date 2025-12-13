package com.retrolad.mediatrontelegrambot;

import lombok.Builder;

@Builder
public record CommandDetails(
        Long chatId,
        Long userTelegramId,
        String username,
        String langCode,
        Command command,
        String data) { }

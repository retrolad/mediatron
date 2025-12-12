package com.retrolad.mediatron.dto;

public record TelegramClientSignInRequest(
        Long telegramId,
        String username
) { }

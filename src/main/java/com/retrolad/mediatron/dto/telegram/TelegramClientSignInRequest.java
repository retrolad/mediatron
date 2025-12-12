package com.retrolad.mediatron.dto.telegram;

public record TelegramClientSignInRequest(
        Long telegramId,
        String username
) { }

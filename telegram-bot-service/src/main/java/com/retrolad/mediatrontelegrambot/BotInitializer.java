package com.retrolad.mediatrontelegrambot;

import com.retrolad.mediatrontelegrambot.config.BotConfig;
import com.retrolad.mediatrontelegrambot.service.MediatronApiService;
import com.retrolad.mediatrontelegrambot.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;

@Configuration
@RequiredArgsConstructor
public class BotInitializer {

    private final BotConfig botConfig;
    private final MediatronApiService mediatronService;
    private final UserProfileService userProfileService;

    @EventListener({ContextRefreshedEvent.class})
    public void init() {

        try (TelegramBotsLongPollingApplication tgApi = new TelegramBotsLongPollingApplication()) {
            MediatronBot bot = new MediatronBot(mediatronService, userProfileService, botConfig.getToken());

            // Регистрируем бота в API, что запускает Long Polling
            System.out.println("Registering bot: " + botConfig.getName());
            tgApi.registerBot(botConfig.getToken(), bot);

            Thread.currentThread().join();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

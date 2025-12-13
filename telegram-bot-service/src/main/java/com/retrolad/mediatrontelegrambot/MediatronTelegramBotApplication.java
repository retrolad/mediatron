package com.retrolad.mediatrontelegrambot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;

@SpringBootApplication
public class MediatronTelegramBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(MediatronTelegramBotApplication.class, args);
    }
}
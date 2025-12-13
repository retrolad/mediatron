package com.retrolad.mediatrontelegrambot;

import com.retrolad.mediatrontelegrambot.dto.MovieInfoResponse;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.List;

public class TelegramApiUtils {

    public static SendMessage simpleMessage(Long chatId, String text) {
        return SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .build();
    }

    public static SendMessage keyboardMessage(Long chatId, String text, InlineKeyboardMarkup keyboard) {
        return SendMessage.builder()
                .chatId(chatId)
                .replyMarkup(keyboard)
                .text(text)
                .build();
    }

    public static String buildMovieInfoHtml(long chatId, MovieInfoResponse movie) {
        String text = new StringBuilder()
                .append("<b>").append(escapeHtml(movie.title())).append("</b>").append("\n")
                .append("<i>Год:</i> ").append(movie.year()).append("\n")
                .append("<i>IMDb:</i> ").append(movie.imdbRating()).append(" ⭐").append("\n")
                .append("<i>Кинопоиск:</i> ").append(movie.kpRating()).append(" ⭐").append("\n")
                .append("\n")
                .append("<code>id: ").append(movie.id()).append("</code>")
                .toString();

        return text;
    }

    public static InlineKeyboardButton createButton(String text, String callback) {
        InlineKeyboardButton button = new InlineKeyboardButton(text);
        button.setCallbackData(callback);
        return button;
    }

    public static InlineKeyboardMarkup createKeyboard(List<InlineKeyboardButton> buttons) {
        return new InlineKeyboardMarkup(buttons.stream()
                .map(InlineKeyboardRow::new).toList());
    }

    private static String escapeHtml(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }
}

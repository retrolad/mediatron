package com.retrolad.mediatrontelegrambot;

import com.retrolad.mediatrontelegrambot.dto.MovieInfoResponse;
import com.retrolad.mediatrontelegrambot.dto.SearchMovieResponse;
import com.retrolad.mediatrontelegrambot.dto.UserMovieDto;
import com.retrolad.mediatrontelegrambot.dto.UserProfile;
import com.retrolad.mediatrontelegrambot.service.MediatronApiService;
import com.retrolad.mediatrontelegrambot.service.UserProfileService;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MediatronBot implements LongPollingSingleThreadUpdateConsumer {

    @FunctionalInterface
    interface Executable {
        void run() throws TelegramApiException;
    }

    private final TelegramClient telegramClient;
    private final MediatronApiService mediatronService;
    private final UserProfileService userProfileService;
    private final Set<String> commands;

    public MediatronBot(MediatronApiService mediatronService, UserProfileService userProfileService,
                        String botToken) {
        this.mediatronService = mediatronService;
        this.userProfileService = userProfileService;
        this.telegramClient = new OkHttpTelegramClient(botToken);

        commands = getCommands();
    }

    @Override
    public void consume(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            handleMessage(update.getMessage());
        } else if (update.hasCallbackQuery()) {
            handleCallbackQuery(update.getCallbackQuery());
        }
    }

    private void handleMessage(Message message) {
        String messageText = message.getText();
        String commandText = messageText.split(" ")[0].substring(1);

        // Если сообщение отправлено из группы, отвечать нужно туда, а не пользователю
        Long id;
        if (message.getChatId().equals(message.getFrom().getId())) {
            id = message.getFrom().getId();
        } else {
            id = message.getChatId();
        }

        if (commands.contains(commandText)) {
            handleCommand(CommandDetails.builder()
                .chatId(id)
                .username(message.getFrom().getUserName())
                .langCode(message.getFrom().getLanguageCode())
                .command(Command.from(commandText))
                .data(messageText.substring(messageText.indexOf(" ") + 1))
                .build());
        }
    }

    private void handleCallbackQuery(CallbackQuery callbackQuery) {
        // Отправляем подтверждение в Telegram, чтобы убрать часики загрузки у кнопки
        AnswerCallbackQuery answer = new AnswerCallbackQuery(callbackQuery.getId());
        try {
            telegramClient.execute(answer);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

        String message = callbackQuery.getData();
        String commandText = message.split(" ")[0].substring(1);

        if (commands.contains(commandText)) {
            handleCommand(CommandDetails.builder()
                    .chatId(callbackQuery.getMessage().getChatId())
                    .langCode(callbackQuery.getFrom().getLanguageCode())
                    .command(Command.from(commandText))
                    .data(message.substring(message.indexOf(" ") + 1))
                    .build());
        }
    }

    private void handleCommand(CommandDetails commandDetails) {
        switch (commandDetails.command()) {
            case SEARCH -> {
                handleSearch(commandDetails.chatId(), commandDetails.data());
            }
            case MOVIE -> {
                handleMovieInfo(
                        Long.valueOf(commandDetails.data()),
                        commandDetails.langCode(),
                        commandDetails.chatId());
            }
            case START -> {
                handleStart(commandDetails.chatId(), commandDetails.username());
            }
            case WATCHLIST -> {
                handleWatchlist(commandDetails.chatId(), commandDetails.langCode());
            }
            case ADD_TO_WATCHLIST -> {
                handleAddToWatchlist(commandDetails.chatId(), Long.valueOf(commandDetails.data()));
            }
            case REMOVE_FROM_WATCHLIST -> {
                handleRemoveFromWatchlist(commandDetails.chatId(), Long.valueOf(commandDetails.data()));
            }
            default -> executeSafe(() -> telegramClient.execute(
                    TelegramApiUtils.simpleMessage(commandDetails.chatId(), "Команда не распознана")));
        }
    }

    private void handleSearch(Long chatId, String messageText) {
        List<SearchMovieResponse> foundMovies = mediatronService.getMovies(
                messageText.replace("/search ", ""));
        List<InlineKeyboardButton> buttons = foundMovies.stream()
                .map(m -> TelegramApiUtils.createButton(
                        String.format("%s (%d)",m.title(), m.year()), "/movie " + m.movieId()))
                .toList();

        SendMessage message = SendMessage.builder()
                .text("Найденные фильмы")
                .replyMarkup(TelegramApiUtils.createKeyboard(buttons))
                .chatId(chatId)
                .build();

        executeSafe(() -> telegramClient.execute(message));
    }

    private void handleMovieInfo(Long movieId, String lang, Long chatId) {
        MovieInfoResponse movie = mediatronService.getMovieInfo(movieId, lang);

        String text = TelegramApiUtils.buildMovieInfoHtml(chatId, movie);
        InlineKeyboardButton button = TelegramApiUtils.createButton(
                "Буду смотреть ✔️", "/add-to-watchlist " + movieId);
        SendMessage msg = SendMessage.builder()
                .chatId(String.valueOf(chatId))
                .text(text)
                .parseMode("HTML")
                .disableWebPagePreview(true)
                .replyMarkup(TelegramApiUtils.createKeyboard(List.of(button)))
                .build();

        executeSafe(() -> telegramClient.execute(msg));
    }

    private void handleStart(Long chatId, String username) {
        UserProfile profile = mediatronService.userCreateProfile(chatId, username);

        String message;
        if (profile != null) {
            message = "Профиль успешно создан!";
            userProfileService.addProfile(chatId, profile);
        } else {
            message = "Вам хватит и одного профиля :)";
        }

        String finalMessage = message;
        executeSafe(() -> telegramClient.execute(
                TelegramApiUtils.simpleMessage(chatId, finalMessage)));
    }

    private void handleWatchlist(Long chatId, String lang) {
        Long userId = getUserIdOrSendWarning(chatId);

        if (userId == null) {
            return;
        }

        List<UserMovieDto> watchlist = mediatronService.getWatchlist(userId, lang);

        if (watchlist.isEmpty()) {
            executeSafe(() -> telegramClient.execute(
                    TelegramApiUtils.simpleMessage(
                            chatId,
                            "Список пуст! Найдите фильм командой /search и добавьте в список.")
            ));
            return;
        }

        List<InlineKeyboardButton> buttons = watchlist.stream()
                .map(m -> TelegramApiUtils.createButton(
                        String.format("%s (%d)",m.title(), m.year()), "/movie " + m.id()))
                .toList();

        List<InlineKeyboardButton> removeButtons = watchlist.stream()
                .map(m -> TelegramApiUtils.createButton("❌", "/remove-from-watchlist " + m.id()))
                .toList();

        List<InlineKeyboardRow> rows = new ArrayList<>();
        for (int i = 0; i < watchlist.size(); i++) {
            rows.add(new InlineKeyboardRow(buttons.get(i), removeButtons.get(i)));
        }

        SendMessage message = TelegramApiUtils.keyboardMessage(
                chatId,
                "Ваш список фильмов на просмотр",
                new InlineKeyboardMarkup(rows));

        executeSafe(() -> telegramClient.execute(message));
    }

    private void handleAddToWatchlist(Long chatId, Long movieId) {
        Long userId = getUserIdOrSendWarning(chatId);

        if (userId == null) {
            return;
        }

        mediatronService.addToWatchlist(userId, movieId);
        executeSafe(() -> telegramClient.execute(
                TelegramApiUtils.simpleMessage(chatId, "Фильм успешно добавлен в список!")
        ));
    }

    private void handleRemoveFromWatchlist(Long chatId, Long movieId) {
        Long userId = getUserIdOrSendWarning(chatId);

        if (userId == null) {
            return;
        }

        mediatronService.removeFromWatchlist(userId, movieId);
        executeSafe(() -> telegramClient.execute(
                TelegramApiUtils.simpleMessage(chatId, "Фильм успешно удален из списка.")
        ));
    }

    private Long getUserIdOrSendWarning(Long chatId) {
        Long userId = userProfileService.getUserId(chatId);

        if (userId == null) {
            executeSafe(() -> telegramClient.execute(
                    TelegramApiUtils.simpleMessage(chatId, "У вас нет профиля. Создайте его командой /start")
            ));
            return null;
        }
        return userId;
    }

    private Set<String> getCommands() {
        return Arrays.stream(Command.values())
                .map(Enum::toString)
                .map(String::toLowerCase)
                .map(s -> s.replaceAll("_", "-"))
                .collect(Collectors.toSet());
    }

    private void executeSafe(Executable executable) {
        try {
            executable.run();
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}

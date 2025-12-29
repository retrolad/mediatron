package com.retrolad.mediatrontelegrambot.service;

import com.retrolad.mediatrontelegrambot.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MediatronApiService {

    private final RestClient restClient;

    public List<SearchMovieResponse> getMovies(String title) {
        List<SearchMovieResponse> result = restClient.get()
                .uri("/api/movies/titles?query={title}", title)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});

        if (result != null) {
            return result;
        }
        return List.of();
    }

    public MovieInfoResponse getMovieInfo(Long movieId, String lang) {
        return restClient.get()
                .uri("/api/movies/cards/{id}?lang={lang}", movieId, lang)
                .retrieve()
                .body(MovieInfoResponse.class);
    }

    /**
     * Создает пользователя по telegram id
     */
    public UserProfile userCreateProfile(Long telegramId, String username) {
        return restClient.post()
                .uri("/auth/client/telegram/create-user")
                .body(new UserSignInRequest(telegramId, username))
                .exchange(handleResponse(UserProfile.class));
    }

    public UserProfile getUserProfile(Long telegramId) {
        return restClient.get()
                .uri("/api/client/users/by-telegram-id/{telegramId}", telegramId)
                .exchange(handleResponse(UserProfile.class));
    }

    public List<UserMovieDto> getWatchlist(Long userId, String lang) {
        return restClient.get()
                .uri("/api/client/users/{userId}/watchlist?lang={lang}", userId, lang)
                .retrieve()
                .body(new ParameterizedTypeReference<List<UserMovieDto>>() {});
    }

    public void addToWatchlist(Long userId, Long movieId) {
        restClient.post()
                .uri("/api/client/users/{userId}/watchlist/add?movieId={movieId}", userId, movieId)
                .retrieve()
                .toBodilessEntity();
    }

    public void removeFromWatchlist(Long userId, Long movieId) {
        restClient.delete()
                .uri("/api/client/users/{userId}/watchlist/delete?movieId={movieId}", userId, movieId)
                .retrieve()
                .toBodilessEntity();
    }

    private <T> RestClient.RequestHeadersSpec.ExchangeFunction<T> handleResponse(Class<T> clazz) {
        return (req, resp) -> {
            HttpStatusCode statusCode = resp.getStatusCode();
            if (statusCode == HttpStatus.CONFLICT || statusCode == HttpStatus.NOT_FOUND)
                return null;
            else if (statusCode == HttpStatus.OK)
                return resp.bodyTo(clazz);
            else
                throw new RuntimeException("Неизвестная ошибка");
        };
    }
}

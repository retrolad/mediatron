package com.retrolad.mediatrontelegrambot.service;

import com.retrolad.mediatrontelegrambot.dto.UserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final MediatronApiService apiService;

    // Кэш пользователей
    private final Map<Long, UserProfile> profiles = new HashMap<>();

    public UserProfile getProfile(Long telegramId) {
        UserProfile userProfile = profiles.get(telegramId);

        if (userProfile != null) {
            return userProfile;
        }

        // Если профиль не кэширован
        userProfile = apiService.getUserProfile(telegramId);

        // Кэшируем, если профиль существует
        if (userProfile != null) {
            addProfile(telegramId, userProfile);
            return userProfile;
        }

        // Профиль не существует
        return null;
    }

    public Long getUserId(Long telegramId) {
        UserProfile profile = getProfile(telegramId);

        if (profile != null) {
            return profile.id();
        }
        return null;
    }

    public void addProfile(Long telegramId, UserProfile profile) {
        profiles.put(telegramId, profile);
    }
}

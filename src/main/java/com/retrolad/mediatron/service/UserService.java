package com.retrolad.mediatron.service;

import com.retrolad.mediatron.dto.UserProfileDto;
import com.retrolad.mediatron.exception.UserProfileNotFoundException;
import com.retrolad.mediatron.mapper.UserProfileMapper;
import com.retrolad.mediatron.model.user.User;
import com.retrolad.mediatron.repository.UserRepository;
import com.retrolad.mediatron.security.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserProfileMapper userMapper;
    private final AuthService authService;

    public UserProfileDto getUserProfile(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElseThrow();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow();
    }

    @Transactional
    public User createUserProfile(AuthUser authUser) {
        User newUser = User.builder()
                .id(authUser.getId())
                .username(UUID.randomUUID().toString())
                .authUser(authUser)
                .build();

        User savedUser = userRepository.save(newUser);

        // Генерируем имя для пользователя
        Long userId = savedUser.getId();
        String username = "user_" + userId;
        savedUser.setUsername(username);

        return savedUser;
    }

    public UserProfileDto getUserByTelegramId(Long telegramId) {
        AuthUser authUser = authService.getByTelegramId(telegramId);
        if (authUser == null) return null;

        return userRepository.findById(authUser.getId())
                .map(userMapper::toDto)
                .orElseThrow(() -> new UserProfileNotFoundException("У пользователя нет профиля: " + authUser.getId()));
    }
}

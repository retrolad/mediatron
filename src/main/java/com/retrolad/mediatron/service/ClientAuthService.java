package com.retrolad.mediatron.service;

import com.retrolad.mediatron.dto.TelegramClientSignInRequest;
import com.retrolad.mediatron.dto.UserProfileDto;
import com.retrolad.mediatron.mapper.UserProfileMapper;
import com.retrolad.mediatron.model.user.User;
import com.retrolad.mediatron.repository.AuthRoleRepository;
import com.retrolad.mediatron.repository.AuthUserRepository;
import com.retrolad.mediatron.security.AuthRole;
import com.retrolad.mediatron.security.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientAuthService {

    private final AuthUserRepository authUserRepository;
    private final AuthRoleRepository roleRepository;
    private final UserService userService;
    private final UserProfileMapper userProfileMapper;

    /**
     * Создает пользователя из телеграм клиента
     * @param request запрос на создание профиля
     */
    public UserProfileDto telegramCreateUser(TelegramClientSignInRequest request) {
        if (userService.getUserByTelegramId(request.telegramId()) != null) {
            return null;
        }

        AuthUser authUser = authUserRepository.findByTelegramId(request.telegramId()).orElse(null);

        if (authUser == null) {
            AuthRole role = roleRepository.findByName("ROLE_USER").orElseThrow();
            authUser = AuthUser.builder()
                    .telegramId(request.telegramId())
                    .isActive(true)
                    .build();
            authUser.getRoles().add(role);

            User userProfile = userService.createUserProfile(authUser);
            return userProfileMapper.toDto(userProfile);
        }
        return null;
    }
}

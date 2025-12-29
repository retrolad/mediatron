package com.retrolad.mediatron.service;

import com.retrolad.mediatron.repository.AuthRoleRepository;
import com.retrolad.mediatron.repository.AuthUserRepository;
import com.retrolad.mediatron.security.AuthRole;
import com.retrolad.mediatron.security.AuthUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOidcUserService extends OidcUserService {

    private final AuthUserRepository userRepository;
    private final AuthRoleRepository roleRepository;
    private final UserService userService;

    @Override
    @Transactional
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);
        String email = oidcUser.getEmail();

        AuthUser authUser = userRepository.findByEmail(email).orElse(null);

        // Регистрируем пользователя, если его не существует
        if (authUser == null) {
            AuthRole role = roleRepository.findByName("ROLE_USER").orElseThrow();
            authUser = AuthUser.builder()
                    .email(email)
                    .isActive(true)
                    .build();
            authUser.getRoles().add(role);

            // Создаем профиль
            userService.createUserProfile(authUser);

            String clientId = userRequest.getClientRegistration().getClientId();
            log.info("Пользователь успешно зарегистрирован через систему OAuth2",
                    kv("userId", authUser.getId()),
                    kv("oauthProvider", clientId));
        }

        return oidcUser;
    }
}

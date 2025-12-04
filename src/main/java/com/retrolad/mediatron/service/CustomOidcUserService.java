package com.retrolad.mediatron.service;

import com.retrolad.mediatron.repository.AuthRoleRepository;
import com.retrolad.mediatron.repository.AuthUserRepository;
import com.retrolad.mediatron.security.AuthRole;
import com.retrolad.mediatron.security.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOidcUserService extends OidcUserService {

    private final AuthUserRepository userRepository;
    private final AuthRoleRepository roleRepository;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);
        String email = oidcUser.getEmail();

        AuthUser user = userRepository.findByEmail(email).orElse(null);

        // Регистрируем пользователя
        if (user == null) {
            AuthRole role = roleRepository.findByName("ROLE_USER").orElseThrow();
            user = AuthUser.builder()
                    .email(email)
                    .isActive(true)
                    .build();
            user.getRoles().add(role);
            userRepository.save(user);
        }

        return oidcUser;
    }
}

package com.retrolad.mediatron.mapper;

import com.retrolad.mediatron.dto.UserProfileDto;
import com.retrolad.mediatron.model.user.User;
import org.springframework.stereotype.Component;

@Component
public class UserProfileMapper {

    public UserProfileDto toDto(User user) {
        return new UserProfileDto(
                user.getId(),
                user.getAuthUser().getEmail(),
                user.getUsername()
        );
    }
}

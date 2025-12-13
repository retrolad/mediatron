package com.retrolad.mediatron.mapper;

import com.retrolad.mediatron.model.movie.Role;
import com.retrolad.mediatron.model.movie.RoleTranslation;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    public String toDto(Role role, String lang) {
        RoleTranslation roleTranslation = role.getTranslations().get(lang);
        if (roleTranslation == null) {
            return role.getDisplayName();
        }
        return roleTranslation.getDisplayName();
    }
}

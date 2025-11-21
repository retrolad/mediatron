package com.retrolad.mediatron.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "person_role_translation")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RoleTranslation {

    @EmbeddedId
    @EqualsAndHashCode.Include
    private RoleTranslationId id;

    @Column(name = "lang_code", insertable = false, updatable = false)
    private String langCode;

    private String displayName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    @MapsId("roleId")
    @ToString.Exclude
    private Role role;
}

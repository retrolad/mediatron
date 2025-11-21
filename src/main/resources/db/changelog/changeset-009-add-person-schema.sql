--liquibase formatted sql

--changeset retrolad:1
create table if not exists person
(
    id             bigint primary key, -- tmdb person id
    original_name  varchar(255),
    birthday       date,
    deathday       date,
    place_of_birth varchar(255),
    profile_image  text
);

--changeset retrolad:2
create table if not exists person_translation
(
    person_id bigint references person (id) on delete cascade,
    lang_code varchar(2) references language (code),
    name      varchar(255),
    biography text,

    primary key (person_id, lang_code)
);

--changeset retrolad:3
create table if not exists movie_person
(
    movie_id       bigint references movie (id),
    person_id      bigint references person (id),
    role_id        int references person_role (id),
    character_name varchar(255),                            -- используется только, если роль "актер"
    billing_order  smallint,                                -- порядок в титрах, если актер
    created_at     timestamp default now(),

    primary key (movie_id, person_id, role_id)              -- одна персона может быть и режиссером и актером
);
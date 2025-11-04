--liquibase formatted sql

--changeset retrolad:1
create table if not exists movie_translation(
    movie_id integer references movie(id) on delete cascade,
    lang_code varchar(2) references language(code),
    title varchar(50) not null,
    description text,
    tagline varchar(255),
    unique (movie_id, lang_code)
);
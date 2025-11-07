--liquibase formatted sql

--changeset retrolad:1
create table if not exists genre
(
    id   serial primary key,
    name varchar(50) unique not null
);

--changeset retrolad:2
create table if not exists genre_translation
(
    genre_id  int references genre (id) on delete cascade,
    lang_code varchar(2) references language (code),
    name      varchar(100) not null,
    unique (genre_id, lang_code)
);

--changeset retrolad:3
create table if not exists movie_genre
(
    movie_id bigint references movie (id) on delete cascade,
    genre_id int references genre (id),
    primary key (movie_id, genre_id)
);
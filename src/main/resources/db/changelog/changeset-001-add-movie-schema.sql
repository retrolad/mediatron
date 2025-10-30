--liquibase formatted sql

--changeset retrolad:1
create table if not exists movie
(
    id bigserial primary key,
    title varchar(255) not null,
    original_title varchar(255) not null,
    release_year integer not null,
    description text,
    kinopoisk_id integer,
    imdb_id integer,
    oscar_count smallint,
    kinopoisk_rating float,
    imdb_rating float,
    interest_group_rating float,
    director varchar(255) not null,
    country jsonb,
    duration smallint not null
);


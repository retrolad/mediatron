--liquibase formatted sql

--changeset retrolad:1
create table if not exists movie
(
    id bigserial primary key,
    title varchar(255) not null,
    original_title varchar(255),
    release_year integer,
    description text,
    kinopoisk_id integer,
    imdb_id integer,
    oscar_count smallint,
    kinopoisk_rating numeric(3,1),
    imdb_rating numeric(3,1),
    interest_group_rating numeric(3,1),
    director varchar(255),
    country jsonb,
    duration smallint
);


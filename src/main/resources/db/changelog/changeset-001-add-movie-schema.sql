--liquibase formatted sql

--changeset retrolad:1
create table if not exists movie
(
    id serial primary key,
    tmdb_id bigint unique not null,
    original_title varchar(255) not null,
    year integer not null,                  -- год релиза
    runtime integer not null,               -- продолжительность в минутах
    rating_mpaa varchar(10),                -- например: 'PG-13', 'R'
    age_rating smallint null,                    -- минимальный возраст например 16
    budget bigint,                          -- бюджет в USD
    original_language varchar(2),           -- ?
    release_date date,                      -- дата в формате ISO (1982-06-25)
    created_at timestamp default now()      -- 2025-11-03 12:39:12.134448 +00
    -- description text,
    -- kinopoisk_id integer,
    -- imdb_id integer,
    -- oscar_count smallint,
    -- kinopoisk_rating float,
    -- imdb_rating float,
    -- interest_group_rating float,
    -- director varchar(255) not null,
    -- country jsonb,
);


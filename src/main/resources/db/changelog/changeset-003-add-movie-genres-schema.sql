--liquibase formatted sql

--changeset retrolad:1
create table if not exists movie_genres
(
    id serial primary key ,
    movie_id bigint references movie(id),
    genre_id int references genre(id)
);
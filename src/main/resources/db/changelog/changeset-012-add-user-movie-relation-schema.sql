--liquibase formatted sql

--changeset retrolad:1
create table user_movie_relation(
    user_id bigint references user_profile(id),
    movie_id bigint references movie(id),
    is_watched boolean default false,
    is_in_watchlist boolean default false,
    is_favourite boolean default false,
    is_rated boolean default false,
    rating int,
    created_at timestamp default now(),
    primary key (user_id, movie_id)
);
--liquibase formatted sql

--changeset retrolad:1
create table if not exists source(
    id serial primary key,
    name varchar(50)
);

--changeset retrolad:2
create table if not exists movie_rating(
    id bigserial primary key,
    movie_id bigint not null references movie(id) on delete cascade,
    source_id int not null references source(id) on delete cascade,

    rating real not null,

    unique (movie_id, source_id)
);

--changeset retrolad:3
create table if not exists movie_votes(
    id bigserial primary key,
    movie_id bigint not null references movie(id) on delete cascade,
    source_id int not null references source(id) on delete cascade,

    votes bigint not null,

    unique (movie_id, source_id)
);

--changeset retrolad:4
create table if not exists movie_external_id(
    id bigserial primary key,
    movie_id bigint not null references movie(id) on delete cascade,
    source_id int not null references source(id) on delete cascade,

    external_id varchar(255) not null,

    unique (movie_id, source_id)
);
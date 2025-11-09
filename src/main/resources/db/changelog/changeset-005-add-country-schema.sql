--liquibase formatted sql

--changeset retrolad:1
create table if not exists production_country
(
    id serial primary key,
    iso_code varchar(2) not null unique,
    russian_name varchar(255) not null              -- название на русском
);

--changeset retrolad:2
create table if not exists production_country_translation
(
    country_id int references production_country(id) on delete cascade,
    lang_code varchar(2) references language(code),
    name varchar(255) not null
);

--changeset retrolad:3
create table if not exists movie_country
(
    movie_id bigint references movie(id) on delete cascade,
    country_id int references production_country(id),
    primary key (movie_id, country_id)
);
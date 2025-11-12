--liquibase formatted sql

--changeset retrolad:1
create table if not exists image_type(
    id serial primary key,
    type varchar(50) unique not null    -- 'poster', 'logo', 'backdrop'
);

--changeset retrolad:2
create table if not exists movie_image(
    id bigserial primary key,
    movie_id bigint references movie(id) on delete cascade,
    type_id int references image_type(id),
    url text not null,
    lang_code varchar(2) references language(code),
    is_default boolean default false,
    created_at timestamp default now()
);
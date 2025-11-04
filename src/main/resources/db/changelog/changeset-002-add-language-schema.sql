--liquibase formatted sql

--changeset retrolad:1
create table if not exists language(
    code char(2) primary key,   -- 'en', 'ru', 'fr'
    name varchar(50) not null,  -- 'English', 'Russian', 'French'
    native_name varchar(50),    -- язык на самом себе: 'English', 'Русский', 'Français'
    created_at timestamp default now()
);
--liquibase formatted sql

--changeset retrolad:1
create table if not exists genre
(
    id serial primary key,
    name varchar(50)
);
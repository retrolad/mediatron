--liquibase formatted sql

--changeset retrolad:1
create table if not exists person_role(
    id serial primary key,
    display_name varchar(50)
);

--changeset retrolad:2
create table if not exists person_role_translation(
    role_id int references person_role(id) on delete cascade,
    lang_code varchar(2) references language(code),
    display_name varchar(50),
    unique (role_id, lang_code)
);
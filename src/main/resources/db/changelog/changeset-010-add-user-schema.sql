--liquibase formatted sql

--changeset retrolad:1
-- данные для аутентификации в spring security
create table if not exists auth_user(
    id bigserial primary key,
    email varchar(255) not null unique,
    password_hash varchar(255) not null,
    is_active boolean not null default true,
    created_at timestamp default now()
--     updated_at timestamp,
--     last_login_at timestamp
);

--changeset retrolad:2
-- доменная сущность
create table if not exists user_profile(
    id bigint primary key references auth_user(id),
    username varchar(100) not null unique
);

--changeset retrolad:3
create table if not exists auth_role(
    id serial primary key,
    name varchar(50) not null unique -- ROLE_USER, ROLE_ADMIN
);

--changeset retrolad:4
create table if not exists auth_user_role(
    user_id bigint references auth_user(id) on delete cascade,
    role_id int references auth_role(id) on delete cascade,
    primary key (user_id, role_id)
);
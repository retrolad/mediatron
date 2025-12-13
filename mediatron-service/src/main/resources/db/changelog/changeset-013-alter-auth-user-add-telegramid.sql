--liquibase formatted sql

--changeset retrolad:1
alter table auth_user
alter column email
drop not null;

--changeset retrolad:2
alter table auth_user
add column telegram_id bigint unique;
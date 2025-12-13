--liquibase formatted sql

--changeset retrolad:1
-- пользователи OIDC могут не иметь пароля
alter table auth_user
alter column password_hash
drop not null;
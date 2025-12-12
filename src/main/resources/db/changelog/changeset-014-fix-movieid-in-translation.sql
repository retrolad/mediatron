--liquibase formatted sql

--changeset retrolad:1
alter table movie_translation
alter column movie_id
type bigint;
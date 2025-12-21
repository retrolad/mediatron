--liquibase formatted sql

--changeset retrolad:1
insert into auth_role(name) values('ROLE_ADMIN'), ('ROLE_USER');
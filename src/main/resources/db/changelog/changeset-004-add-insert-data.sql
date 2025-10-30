--liquibase formatted sql

--changeset retrolad:1
insert into movie (title, original_title, release_year, director, duration) values
('The Shawshank Redemption', 'The Shawshank Redemption', 1994, 'Frank Darabont', 142),
('The Godfather', 'The Godfather', 1972, 'Francis Ford Coppola', 175),
('The Dark Knight', 'The Dark Knight', 2008, 'Christopher Nolan', 152),
('Pulp Fiction', 'Pulp Fiction', 1994, 'Quentin Tarantino', 154),
('Forrest Gump', 'Forrest Gump', 1994, 'Robert Zemeckis', 142),
('Inception', 'Inception', 2010, 'Christopher Nolan', 148),
('The Matrix', 'The Matrix', 1999, 'The Wachowskis', 136),
('Fight Club', 'Fight Club', 1999, 'David Fincher', 139),
('The Lord of the Rings: The Return of the King', 'The Lord of the Rings: The Return of the King', 2003, 'Peter Jackson', 201),
( 'Interstellar', 'Interstellar', 2014, 'Christopher Nolan', 169);

--changeset retrolad:2
insert into genre (name) values
('Drama'),
('Crime'),
('Action'),
('Adventure'),
('Sci-Fi'),
('Fantasy'),
('Thriller'),
('Romance'),
('Comedy'),
('Mystery');

--changeset retrolad:3
insert into movie_genres (movie_id, genre_id) values
(1, 1),
(1, 2),
(2, 1),
(2, 2),
(3, 3),
(3, 7),
(4, 2),
(4, 9),
(5, 1),
(5, 8),
(6, 3),
(6, 5),
(7, 3),
(7, 5),
(8, 1),
(8, 7),
(9, 4),
(9, 6),
(10, 3),
(10, 5);
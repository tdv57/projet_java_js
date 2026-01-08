DROP TABLE IF EXISTS History CASCADE;
DROP TABLE IF EXISTS Film CASCADE;
DROP TABLE IF EXISTS APP_USER CASCADE;
DROP TABLE IF EXISTS Director CASCADE;
DROP TABLE IF EXISTS Genre CASCADE;

CREATE TABLE IF NOT EXISTS Director(id INT primary key auto_increment, surname VARCHAR(100), name VARCHAR(100), birthdate TIMESTAMP, famous BOOLEAN);


CREATE TABLE IF NOT EXISTS APP_USER(id INT primary key auto_increment, surname VARCHAR(100), name VARCHAR(100), password VARCHAR(100), roles VARCHAR(100));

CREATE TABLE IF NOT EXISTS Film(id INT primary key auto_increment, title VARCHAR(100), duration INT, director_id INT, genre_id INT);

CREATE TABLE IF NOT EXISTS Genre(id INT primary key auto_increment, name VARCHAR(100));

CREATE TABLE IF NOT EXISTS History(id INT primary key auto_increment, film_id INT, user_id INT, rating INT);

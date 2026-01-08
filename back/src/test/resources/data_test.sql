DROP TABLE IF EXISTS History;
DROP TABLE IF EXISTS Film;
DROP TABLE IF EXISTS Director;
DROP TABLE IF EXISTS Genre;

-- Database pour les tests
CREATE TABLE IF NOT EXISTS Director(id INT primary key auto_increment, surname VARCHAR(100), name VARCHAR(100), birthdate TIMESTAMP, famous BOOLEAN);
INSERT INTO Director(surname, name, birthdate, famous) VALUES('Cameron', 'James', '1954-08-16', false);
INSERT INTO Director(surname, name, birthdate, famous) VALUES('Jackson', 'Peter', '1961-10-31', true);

CREATE TABLE IF NOT EXISTS Film(id INT primary key auto_increment, title VARCHAR(100), duration INT, director_id INT, genre_id INT);
INSERT INTO Film(title, duration, director_id, genre_id) VALUES('avatar', 162, 1, 1);
INSERT INTO Film(title, duration, director_id, genre_id) VALUES('La communauté de l''anneau', 178, 2, 5);
INSERT INTO Film(title, duration, director_id, genre_id) VALUES('Les deux tours', 179, 2, 5);
INSERT INTO Film(title, duration, director_id, genre_id) VALUES('Le retour du roi', 201, 2, 5);

CREATE TABLE IF NOT EXISTS Genre(id INT primary key auto_increment, name VARCHAR(100));
INSERT INTO Genre(name) VALUES('action');
INSERT INTO Genre(name) VALUES('biopic');
INSERT INTO Genre(name) VALUES('comédie');
INSERT INTO Genre(name) VALUES('drame');
INSERT INTO Genre(name) VALUES('fantaisie');
INSERT INTO Genre(name) VALUES('horreur');
INSERT INTO Genre(name) VALUES('policier');
INSERT INTO Genre(name) VALUES('SF');
INSERT INTO Genre(name) VALUES('thriller');

CREATE TABLE IF NOT EXISTS User(id INT primary key auto_increment, username VARCHAR(100), surname VARCHAR(100), name VARCHAR(100), hash VARCHAR(100), roles VARCHAR(100));
INSERT INTO User(surname, name) VALUES('Test', 'Test');

CREATE TABLE IF NOT EXISTS History(id INT primary key auto_increment, film_id INT, user_id INT, rating INT);
INSERT INTO History(film_id, user_id, rating) VALUES(2, 1, 20);

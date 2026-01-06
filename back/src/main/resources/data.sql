-- Director table
CREATE TABLE IF NOT EXISTS Director(id INT primary key auto_increment, surname VARCHAR(100), name VARCHAR(100), birthdate TIMESTAMP, famous BOOLEAN);
INSERT INTO Director(surname, name, birthdate, famous) VALUES('Cameron', 'James', '1954-08-16', false);
INSERT INTO Director(surname, name, birthdate, famous) VALUES('Jackson', 'Peter', '1961-10-31', true);
INSERT INTO Director(surname, name, birthdate, famous) VALUES('Trachtenberg', 'Dan', '1981-05-11', false);
INSERT INTO Director(surname, name, birthdate, famous) VALUES('Chu', 'Jon', '1979-11-02', false);
INSERT INTO Director(surname, name, birthdate, famous) VALUES('Moore', 'Rich', '1963-05-10', false);
INSERT INTO Director(surname, name, birthdate, famous) VALUES('Gansel', 'Denis', '1973-10-04', false);
INSERT INTO Director(surname, name, birthdate, famous) VALUES('Caruso', 'D.J.', '1965-01-17', false);
INSERT INTO Director(surname, name, birthdate, famous) VALUES('Safdie', 'Joshua', '1984-04-03', false);
INSERT INTO Director(surname, name, birthdate, famous) VALUES('Dowse', 'Michael', '1973-04-19', false);
INSERT INTO Director(surname, name, birthdate, famous) VALUES('Sotozaki', 'Haruo', '1982-04-12', false);
INSERT INTO Director(surname, name, birthdate, famous) VALUES('Sotozaki', 'Haruo', '1974-10-31', false);

-- User table
CREATE TABLE IF NOT EXISTS User(id INT primary key auto_increment, surname VARCHAR(100), name VARCHAR(100), hash VARCHAR(100), roles VARCHAR(100));
INSERT INTO User(surname, name) VALUES('Axel', 'Richard');
INSERT INTO User(surname, name) VALUES('Benoit', 'Boero');
INSERT INTO User(surname, name, roles) VALUES('Elfie', 'Molina--Bonnefoy', 'ADMIN');
INSERT INTO User(surname, name) VALUES('Ferdinand', 'Alain');

-- Film table
CREATE TABLE IF NOT EXISTS Film(id INT primary key auto_increment, title VARCHAR(100), duration INT, director_id INT, genre_id INT);
INSERT INTO Film(title, duration, director_id, genre_id) VALUES('Predator: Badlands', 107, 3, 1);
INSERT INTO Film(title, duration, director_id, genre_id) VALUES('Avatar', 162, 1, 8);
INSERT INTO Film(title, duration, director_id, genre_id) VALUES('Avatar: la Voie de l''eau', 192, 1, 8);
INSERT INTO Film(title, duration, director_id, genre_id) VALUES('Avatar: de Feu et de Cendre', 197, 1, 8);
INSERT INTO Film(title, duration, director_id, genre_id) VALUES('Terminator', 107, 1, 8);
INSERT INTO Film(title, duration, director_id, genre_id) VALUES('Terminator 2: le Jugement dernier', 137, 1, 8);
INSERT INTO Film(title, duration, director_id, genre_id) VALUES('Terminator 3: le Soulèvement des machines', 109, 1, 8);
INSERT INTO Film(title, duration, director_id, genre_id) VALUES('Titanic', 195, 1, 4);
INSERT INTO Film(title, duration, director_id, genre_id) VALUES('La communauté de l''anneau', 178, 2, 5);
INSERT INTO Film(title, duration, director_id, genre_id) VALUES('Les deux tours', 179, 2, 5);
INSERT INTO Film(title, duration, director_id, genre_id) VALUES('Le retour du Roi', 201, 2, 5);
INSERT INTO Film(title, duration, director_id, genre_id) VALUES('Le Hobbit: un voyage inatendu', 182, 2, 5);
INSERT INTO Film(title, duration, director_id, genre_id) VALUES('Le Hobbit: la Désolation de Smaug', 186, 2, 5);
INSERT INTO Film(title, duration, director_id, genre_id) VALUES('Le Hobbit: La bataille des Cinq Armées', 164, 2, 5);
INSERT INTO Film(title, duration, director_id, genre_id) VALUES('Wicked 2', 137, 4, 5);
INSERT INTO Film(title, duration, director_id, genre_id) VALUES('Wicked', 160, 4, 5);
INSERT INTO Film(title, duration, director_id, genre_id) VALUES('Zootopie', 108, 5, 3);
INSERT INTO Film(title, duration, director_id, genre_id) VALUES('Zootopie 2', 110, 5, 3);
INSERT INTO Film(title, duration, director_id, genre_id) VALUES('The Tank', 116, 6, 1);
INSERT INTO Film(title, duration, director_id, genre_id) VALUES('xXx: Reactivated', 107, 7, 1);
INSERT INTO Film(title, duration, director_id, genre_id) VALUES('Marty Supreme', 149, 8, 4);
INSERT INTO Film(title, duration, director_id, genre_id) VALUES('Trap House', 102, 9, 1);
INSERT INTO Film(title, duration, director_id, genre_id) VALUES('Demon Slayer: Kimetsu no Yaiba Infinity Castle', 157, 10, 5);
INSERT INTO Film(title, duration, director_id, genre_id) VALUES('Insaisissables', 115, 11, 9);
INSERT INTO Film(title, duration, director_id, genre_id) VALUES('Insaisissables 2', 129, 11, 9);
INSERT INTO Film(title, duration, director_id, genre_id) VALUES('Insaisissables 3', 113, 11, 9);

-- Genre table
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

-- History table
CREATE TABLE IF NOT EXISTS History(id INT primary key auto_increment, film_id INT, user_id INT, rating INT);
INSERT INTO History(film_id, user_id, rating) VALUES(2, 1, 20);

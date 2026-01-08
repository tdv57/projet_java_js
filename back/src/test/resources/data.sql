-- Director table
INSERT INTO Director(surname, name, birthdate, famous) VALUES('Cameron', 'James', '1954-08-16', false);
INSERT INTO Director(surname, name, birthdate, famous) VALUES('Jackson', 'Peter', '1961-10-31', true);

-- User table
INSERT INTO APP_USER(surname, name, password, roles) VALUES('Axel', 'Richard', 'axel', 'USER');
INSERT INTO APP_USER(surname, name, password, roles) VALUES('Benoit', 'Boero', 'benoit', 'USER');
INSERT INTO APP_USER(surname, name, password, roles) VALUES('Elfie', 'Molina--Bonnefoy', 'elfie', 'ADMIN');
INSERT INTO APP_USER(surname, name, password, roles) VALUES('Ferdinand', 'Alain', 'ferdinand', 'USER');

-- Film table
INSERT INTO Film(title, duration, director_id, genre_id) VALUES('avatar', 162, 1, 1);
INSERT INTO Film(title, duration, director_id, genre_id) VALUES('La communauté de l''anneau', 178, 2, 5);
INSERT INTO Film(title, duration, director_id, genre_id) VALUES('Les deux tours', 179, 2, 5);
INSERT INTO Film(title, duration, director_id, genre_id) VALUES('Le retour du roi', 201, 2, 5);

-- Genre table
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
INSERT INTO History(film_id, user_id, rating) VALUES(2, 1, 20);
INSERT INTO History(film_id, user_id, rating) VALUES(4, 4, 18);
INSERT INTO History(film_id, user_id, rating) VALUES (1, 1, 16);
INSERT INTO History(film_id, user_id, rating) VALUES(2,2,18);
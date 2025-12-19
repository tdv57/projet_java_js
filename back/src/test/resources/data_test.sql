-- Data pour les tests
CREATE TABLE IF NOT EXISTS Director(id INT primary key auto_increment, surname VARCHAR(100), name VARCHAR(100), birthdate TIMESTAMP, famous BOOLEAN);
INSERT INTO Director(surname, name, birthdate, famous) VALUES('Cameron', 'James', '1954-08-16', false);
INSERT INTO Director(surname, name, birthdate, famous) VALUES('Jackson', 'Peter', '1961-10-31', true);

CREATE TABLE IF NOT EXISTS Film(id INT primary key auto_increment, title VARCHAR(100), duration INT, director_id INT);
INSERT INTO Film(title, duration, director_id) VALUES('avatar', 162, 1);
INSERT INTO Film(title, duration, director_id) VALUES('La communauté de l''anneau', 178, 2);
INSERT INTO Film(title, duration, director_id) VALUES('Les deux tours', 179, 2);
INSERT INTO Film(title, duration, director_id) VALUES('Le retour du roi', 201, 2);

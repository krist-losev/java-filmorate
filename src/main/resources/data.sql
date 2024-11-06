BEGIN;
INSERT INTO mpa (mpa_name)
VALUES ('G'),
       ('PG'),
       ('PG-13'),
       ('R'),
       ('NC-17');
COMMIT;

INSERT INTO genres(id, genre_name)
SELECT 1, 'Комедия' WHERE NOT EXISTS (SELECT id FROM genres WHERE id = 1);
INSERT INTO genres(id, genre_name)
SELECT 2, 'Драма' WHERE NOT EXISTS (SELECT id FROM genres WHERE id = 2);
INSERT INTO genres(id, genre_name)
SELECT 3, 'Боевик' WHERE NOT EXISTS (SELECT id FROM genres WHERE id = 3);
INSERT INTO genres(id, genre_name)
SELECT 4, 'Мультфильм' WHERE NOT EXISTS (SELECT id FROM genres WHERE id = 4);
INSERT INTO genres(id, genre_name)
SELECT 5, 'Триллер' WHERE NOT EXISTS (SELECT id FROM genres WHERE id = 5);
INSERT INTO genres(id, genre_name)
SELECT 6, 'Документальный' WHERE NOT EXISTS (SELECT id FROM genres WHERE id = 6);
INSERT INTO mpa(id, mpa_name)
SELECT 1, 'G' WHERE NOT EXISTS (SELECT id FROM mpa WHERE id = 1);
INSERT INTO mpa(id, mpa_name)
SELECT 2, 'PG' WHERE NOT EXISTS (SELECT id FROM mpa WHERE id = 2);
INSERT INTO mpa(id, mpa_name)
SELECT 3, 'PG-13' WHERE NOT EXISTS (SELECT id FROM mpa WHERE id = 3);
INSERT INTO mpa(id, mpa_name)
SELECT 4, 'R' WHERE NOT EXISTS (SELECT id FROM mpa WHERE id = 4);
INSERT INTO mpa(id, mpa_name)
SELECT 5, 'NC-17' WHERE NOT EXISTS (SELECT id FROM mpa WHERE id = 5);

INSERT INTO genres(id, genre_name) VALUES
(1, 'Комедия'),
(2, 'Драма'),
(3, 'Боевик'),
(4, 'Мультфильм'),
(5, 'Триллер'),
(6, 'Документальный');

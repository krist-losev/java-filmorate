package ru.yandex.practicum.filmorate.storage.dal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.interfaces.FilmStorage;
import ru.yandex.practicum.filmorate.validator.FilmValidator;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class FilmDbStorage extends BaseDdStorage<Film> implements FilmStorage {
    private static final String FIND_ALL_FILMS = "SELECT * FROM films AS f LEFT JOIN mpa AS m ON f.mpa_id = m.id_mpa";
    private static final String FIND_FILM_BY_ID_QUERY = "SELECT * FROM films AS f LEFT JOIN mpa " +
            "ON f.mpa_id = mpa.id_mpa WHERE f.id = ?";
    private static final String INSERT_FILM_QUERY = "INSERT INTO films (name, description, releaseDate, " +
            "duration, mpa_id) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE films SET name = ?, description = ?, releaseDate = ?, "
        + "duration = ?, mpa_id = ? WHERE id = ?";
    private static final String ADD_LIKE_FILM = "INSERT INTO likes (film_id, user_id) VALUES (?, ?)";
    private static final String DELETE_LIKE_ID = "DELETE FROM likes WHERE film_id = ? AND user_id = ?";
    private static final String MOST_POPULAR_FILM = "SELECT * FROM films AS f LEFT JOIN mpa AS m " +
            "ON f.mpa_id = m.id_mpa LEFT JOIN (SELECT film_id, COUNT(user_id) AS like_count FROM likes " +
            "GROUP BY film_id) fl ON f.film_id = fl.film_id ORDER BY like_count DESC LIMIT ?";
    FilmValidator validator = new FilmValidator();
    GenreDdStorage genreDdStorage;

    @Autowired
    public FilmDbStorage(JdbcTemplate jdbc, RowMapper<Film> mapper, GenreDdStorage genreDdStorage) {
        super(jdbc, mapper);
        this.genreDdStorage = genreDdStorage;
    }

    @Override
    public Film createFilm(Film film) {
        validator.validate(film);
        long id = insert(
                INSERT_FILM_QUERY,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId());
        film.setId(id);
        return film;
    }

    @Override
    public List<Film> listFilms() {
        List<Film> films = findMany(FIND_ALL_FILMS);
        for (Film film : films) {
            Set<Genre> genres = genreDdStorage.addAllGenresToFilm(film.getId());
            if (!genres.isEmpty()) {
                film.setGenres(genres);
            }
        }
        return films;
    }

    @Override
    public Film updateFilm(Film newFilm) {
        validator.validate(newFilm);
        Optional<Film> film = findOne(FIND_FILM_BY_ID_QUERY, newFilm.getId());
        if (film.isPresent()) {
            update(UPDATE_QUERY,
                    newFilm.getName(),
                    newFilm.getDescription(),
                    newFilm.getReleaseDate(),
                    newFilm.getDuration(),
                    newFilm.getMpa().getId(),
                    newFilm.getId());
        } else {
            throw new NotFoundException("Пользователь не найден.");
        }
        return newFilm;
    }

    @Override
    public Optional<Film> findFilmById(long filmId) {
        Film film = findOne(FIND_FILM_BY_ID_QUERY, filmId).get();
        film.setGenres(genreDdStorage.addAllGenresToFilm(filmId));
        return Optional.of(film);
    }

    public void addLikeFilm(long filmId, long userId) {
        insertKeys(ADD_LIKE_FILM, filmId, userId);
    }

    public void deletedLike(long filmId, long userId) {
        update(DELETE_LIKE_ID, filmId, userId);
    }

    public List<Film> mostPopularFilms(int count) {
        return findMany(MOST_POPULAR_FILM, count);
    }
}


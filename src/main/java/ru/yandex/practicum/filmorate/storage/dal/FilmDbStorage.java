package ru.yandex.practicum.filmorate.storage.dal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.validator.FilmValidator;

import java.util.List;
import java.util.Optional;

@Repository("filmDbStorage")
public class FilmDbStorage extends BaseDdStorage<Film> implements FilmStorage {
    private static final String FIND_ALL_QUERY = "SELECT * FROM films";
    private static final String FIND_FILM_BY_ID_QUERY = "SELECT * FROM films WHERE id = ?";
    private static final String INSERT_FILM_QUERY = "INSERT INTO films (name, description, releaseDate, duration)"
            + " VALUES (?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE films SET name = ?, description = ?, releaseDate = ? "
        + "duration = ?, WHERE id = ?";
    private static final String ADD_LIKE_FILM = "INSERT INTO likes (film_id, user_id) VALUES (?, ?)";
    private static final String DELETE_LIKE_ID = "DELETE FROM likes WHERE film_id = ? AND user_id = ?";
    private static final String FIND_MOST_POPULAR_FILM = "SELECT f.*, mpa.id AS mpa_id, mpa.name_mpa AS mpa_name," +
            " COUNT(l.user_id) AS like FROM films AS f INNER JOIN mpa ON f.mpa= mpa.id INNER JOIN likes AS l " +
            "ON l.film_id = f.id GROUP BY f.id ORDER BY like DESC LIMIT ?";
    FilmValidator validator = new FilmValidator();
    MpaDdStorage mpaDdStorage;
    GenreDdStorage genreDdStorage;

    @Autowired
    public FilmDbStorage(JdbcTemplate jdbc, RowMapper<Film> mapper,
                         MpaDdStorage mpaDdStorage, GenreDdStorage genreDdStorage) {
        super(jdbc, mapper);
        this.mpaDdStorage = mpaDdStorage;
        this.genreDdStorage = genreDdStorage;
    }

    @Override
    public Film createFilm(Film film) {
        validator.validate(film);
        int id = (int) insert(
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
        return findMany(FIND_ALL_QUERY);
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
                    newFilm.getMpa().getId());
        } else {
            throw new NotFoundException("Пользователь не найден.");
        }
        return newFilm;
    }

    @Override
    public Optional<Film> findFilmById(int filmId) {
        return findOne(FIND_FILM_BY_ID_QUERY, filmId);
    }

    public void addLikeFilm(int filmId, int userId) {
        update(ADD_LIKE_FILM, filmId, userId);
    }

    public void deletedLike(int filmId, int userId) {
        update(DELETE_LIKE_ID, filmId, userId);
    }

    public List<Film> popularFilm(int count) {
        return findMany(FIND_MOST_POPULAR_FILM, count);
    }

    @Override
    public List<Genre> listGenre() {
        return genreDdStorage.listGenres();
    }

    @Override
    public Optional<Genre> findGenreById(int genreId) {
        return genreDdStorage.findGenreById(genreId);
    }

    @Override
    public List<Mpa> listMpa() {
        return mpaDdStorage.listMpa();
    }

    @Override
    public Optional<Mpa> findMpaById(int mpaId) {
        return mpaDdStorage.findMpaById(mpaId);
    }
}


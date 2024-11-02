package ru.yandex.practicum.filmorate.storage;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.validator.FilmValidator;

import java.util.*;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();
    private final Map<Integer, Genre> genres = new HashMap<>();
    private final Map<Integer, Mpa> mpas = new HashMap<>();
    private final FilmValidator validator = new FilmValidator();
    private int filmId = 1;

    //создание фильма
    @Override
    public Film createFilm(Film film) {
        log.info("Поступил запрос на добавление фильма: " + film.getName());
        validator.validate(film);
        film.setId(filmId++);
        film.setLike(new HashSet<>());
        films.put(film.getId(), film);
        return film;
    }

    //получение списка всех фильмов
    @Override
    public List<Film> listFilms() {
        return new ArrayList<>(films.values());
    }

    //обновление фильма
    @Override
    public Film updateFilm(Film newFilm) {
        log.info("Пришёл запрос на обновление фильма");
        validator.validate(newFilm);
        if (newFilm.getId() == 0) {
            log.error("Id не может быть равен 0");
            throw new ConditionsNotMetException("Не указан id нового фильма.");
        } else {
            if (films.containsKey(newFilm.getId())) {
                Film oldFilm = films.get(newFilm.getId());
                oldFilm.setName(newFilm.getName());
                oldFilm.setDescription(newFilm.getDescription());
                oldFilm.setReleaseDate(newFilm.getReleaseDate());
                oldFilm.setDuration(newFilm.getDuration());
                return oldFilm;
            } else {
                log.error("Фильм с таким id " + newFilm.getId() + " не найден.");
                throw new NotFoundException("Фильм не найден.");
            }
        }
    }

    //поиск фильма по id
    @Override
    public Optional<Film> findFilmById(int filmId) {
        log.info("Пришёл запрос на поиск фильма");
        return Optional.of(films.values().stream()
                .filter(film -> film.getId() == filmId)
                .findFirst())
                .get();
    }

    //получение списка всех жанров
    @Override
    public List<Genre> listGenre() {
        return new ArrayList<>(genres.values());
    }

    //получение жанра по идентификатору
    @Override
    public Optional<Genre> findGenreById(int genreId) {
        log.info("Пришёл запрос на поиск жанра");
        return Optional.of(genres.values().stream()
                .filter(genre -> genre.getId() == genreId)
                .findFirst())
                .get();
    }

    //получение списка всех вохрастных ограничений
    @Override
    public List<Mpa> listMpa() {
        return new ArrayList<>(mpas.values());
    }

    //получение ограничения по идентификатору
    @Override
    public Optional<Mpa> findMpaById(int mpaId) {
        return Optional.of(mpas.values().stream()
                .filter(mpa -> mpa.getId() == mpaId)
                .findFirst()
                .get());
    }

}

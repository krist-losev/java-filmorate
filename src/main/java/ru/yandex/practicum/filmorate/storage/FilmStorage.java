package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;
import java.util.Optional;

public interface FilmStorage {
    Film createFilm(Film film);

    List<Film> listFilms();

    Film updateFilm(Film film);

    Optional<Film> findFilmById(int filmId);

    List<Genre> listGenre();

    Optional<Genre> findGenreById(int genreId);

    List<Mpa> listMpa();

    Optional<Mpa> findMpaById(int mpaId);
}

package ru.yandex.practicum.filmorate.storage.interfaces;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;

public interface FilmStorage {
    Film createFilm(Film film);

    List<Film> listFilms();

    Film updateFilm(Film film);

    Optional<Film> findFilmById(long filmId);

    void addLikeFilm(long filmId, long userId);

    void deletedLike(long filmId, long userId);
}

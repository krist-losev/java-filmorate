package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.dal.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.dal.UserDbStorage;

import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class FilmService {

    private FilmDbStorage filmDbStorage;
    private UserDbStorage userDbStorage;
    private static final Comparator<Film> LIKES_FILM = Comparator.comparing(film -> film.getLike().size(), Comparator.reverseOrder());

    public void addLike(int filmId, int userId) {
        userDbStorage.findUserById(userId).orElseThrow(() -> new NotFoundException("Пользователь не найден."));
        filmDbStorage.addLikeFilm(filmId, userId);
        log.info("Фильму " + filmId + " добавлен лайк пользователя " + userId);
    }

    public void deleteLike(int filmId, int userId) {
        userDbStorage.findUserById(userId).orElseThrow(() -> new NotFoundException("Пользователь не найден."));
        filmDbStorage.deletedLike(filmId, userId);
        log.info("У фильма " + filmId + " удалён лайк пользователя " + userId);
    }

    public List<Film> mostPopularFilm(int count) {
        return filmDbStorage.popularFilm(count);
    }

    public Film createFilm(Film film) {
        return filmDbStorage.createFilm(film);
    }

    public List<Film> listFilms() {
        return filmDbStorage.listFilms();
    }

    public Film updateFilm(Film newFilm) {
        return filmDbStorage.updateFilm(newFilm);
    }

    public Film findFilmById(int id) {
        return filmDbStorage.findFilmById(id).orElseThrow(() -> new NotFoundException("Фильм с таким id не найден."));
    }
}

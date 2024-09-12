package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class FilmService {

    private FilmStorage filmStorage;
    private UserStorage userStorage;
    private static final Comparator<Film> LIKES_FILM = Comparator.comparing(film -> film.getLike().size(), Comparator.reverseOrder());

    public void addLike(int filmId, int userId) {
        userStorage.findUserById(userId).orElseThrow(() -> new NotFoundException("Пользователь не найден."));
        findFilmById(filmId)
                .getLike()
                .add(userId);
        log.info("Фильму " + filmId + " добавлен лайк пользователя " + userId);
    }

    public void deleteLike(int filmId, int userId) {
        userStorage.findUserById(userId).orElseThrow(() -> new NotFoundException("Пользователь не найден."));
        findFilmById(filmId)
                .getLike()
                .remove(userId);
        log.info("У фильма " + filmId + " удалён лайк пользователя " + userId);
    }

    public List<Film> mostPopularFilm(int count) {
        return listFilms()
                .stream()
                .sorted(LIKES_FILM)
                .limit(count)
                .collect(Collectors.toList());
    }

    public Film createFilm(Film film) {
        return filmStorage.createFilm(film);
    }

    public List<Film> listFilms() {
        return filmStorage.listFilms();
    }

    public Film updateFilm(Film newFilm) {
        return filmStorage.updateFilm(newFilm);
    }

    public Film findFilmById(int id) {
        return filmStorage.findFilmById(id).orElseThrow(() -> new NotFoundException("Фильм с таким id не найден."));
    }
}

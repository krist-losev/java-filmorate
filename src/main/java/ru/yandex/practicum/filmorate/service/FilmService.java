package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.mappers.FilmMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.dal.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.dal.UserDbStorage;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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

    public List<FilmDto> mostPopularFilm(int count) {
        return filmDbStorage.popularFilm(count).stream().map(FilmMapper::mapToFilmDto).toList();
    }

    public FilmDto createFilm(Film film) {
        Optional<Film> newFilm = Optional.of(filmDbStorage.createFilm(film));
        return newFilm.map(FilmMapper::mapToFilmDto).get();
    }

    public List<FilmDto> listFilms() {
        return filmDbStorage.listFilms().stream().map(FilmMapper::mapToFilmDto).toList();
    }

    public FilmDto updateFilm(Film newFilm) {
        Optional<Film> film = Optional.of(filmDbStorage.updateFilm(newFilm));
        return film.map(FilmMapper::mapToFilmDto).get();
    }

    public FilmDto findFilmById(int id) {
        return filmDbStorage.findFilmById(id)
                .map(FilmMapper::mapToFilmDto)
                .orElseThrow(() -> new NotFoundException("Фильм с таким id не найден."));
    }
}

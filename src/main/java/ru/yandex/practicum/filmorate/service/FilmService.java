package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.dal.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.dal.GenreDdStorage;
import ru.yandex.practicum.filmorate.storage.dal.MpaDdStorage;
import ru.yandex.practicum.filmorate.storage.dal.UserDbStorage;

import java.util.List;

@Service
@Slf4j
public class FilmService {

    private final FilmDbStorage filmDbStorage;
    private final UserDbStorage userDbStorage;
    private final GenreDdStorage genreDdStorage;
    private final MpaDdStorage mpaDdStorage;

    public FilmService(FilmDbStorage filmDbStorage, UserDbStorage userDbStorage,
                       GenreDdStorage genreDdStorage, MpaDdStorage mpaDdStorage) {
        this.filmDbStorage = filmDbStorage;
        this.userDbStorage = userDbStorage;
        this.genreDdStorage = genreDdStorage;
        this.mpaDdStorage = mpaDdStorage;
    }

    public void addLike(long filmId, long userId) {
        userDbStorage.findUserById(userId).orElseThrow(() -> new NotFoundException("Пользователь не найден."));
        filmDbStorage.addLikeFilm(filmId, userId);
        log.info("Фильму " + filmId + " добавлен лайк пользователя " + userId);
    }

    public void deleteLike(long filmId, long userId) {
        userDbStorage.findUserById(userId).orElseThrow(() -> new NotFoundException("Пользователь не найден."));
        filmDbStorage.deletedLike(filmId, userId);
        log.info("У фильма " + filmId + " удалён лайк пользователя " + userId);
    }

    public List<Film> mostPopularFilm(int count) {
        return filmDbStorage.mostPopularFilms(count);
    }

    public Film createFilm(Film film) {
        if (film.getMpa() != null) {
            mpaDdStorage.findMpaById(film.getMpa().getId()).orElseThrow(() ->
                    new ValidException("Данный MPA не существует"));
        }
        Film newFilm = filmDbStorage.createFilm(film);
        if (!newFilm.getGenres().isEmpty()) {
            createGenre(film);
        }
        return newFilm;
    }

    public List<Film> listFilms() {
        return filmDbStorage.listFilms();
    }

    public Film updateFilm(Film newFilm) {
        findFilmById(newFilm.getId());
        Film film = filmDbStorage.updateFilm(newFilm);
        if (!film.getGenres().isEmpty()) {
            genreDdStorage.deletedGenreFilm(newFilm.getId());
            createGenre(newFilm);
        }
        return film;
    }

    public Film findFilmById(long id) {
        return filmDbStorage.findFilmById(id)
                .orElseThrow(() -> new NotFoundException("Фильм с таким id не найден."));
    }

    private void createGenre(Film film) {
        List<Integer> genres = genreDdStorage.listGenres().stream().map(Genre::getId).toList();
        List<Integer> filmGenre = film.getGenres().stream().map(Genre::getId).toList();
        if (!genres.containsAll(filmGenre)) {
            throw new ValidException("Жанр не найден");
        }
        for (Integer idGenre : genres) {
            if (filmGenre.contains(idGenre)) {
                genreDdStorage.addGenreToFilm(film.getId(), idGenre);
            }
        }
    }
}

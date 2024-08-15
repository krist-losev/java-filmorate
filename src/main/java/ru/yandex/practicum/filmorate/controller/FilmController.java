package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validator.FilmValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {

    private Map<Integer, Film> films = new HashMap<>();
    private FilmValidator validator = new FilmValidator();

    private int filmId = 1;

    //добавление фильма
    @PostMapping
    public Film createFilm(@RequestBody Film film) {
        log.info("Поступил запрос на добавление фильма: " + film.getName());
        validator.validate(film);
        film.setId(filmId++);
        films.put(film.getId(), film);
        return film;
    }

    //получение списка всех добавленных фильмов
    @GetMapping
    public List<Film> listFilms() {
        return new ArrayList<>(films.values());
    }

    //обновление фильма
    @PutMapping
    public Film updateFilm(@RequestBody Film newFilm) {
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
}

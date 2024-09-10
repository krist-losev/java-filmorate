package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/films")
@Slf4j
@AllArgsConstructor
public class FilmController {

    private FilmService filmService;

    //добавление фильма
    @PostMapping
    public Film createFilm(@RequestBody Film film) {
        return filmService.createFilm(film);
    }

    //получение списка всех добавленных фильмов
    @GetMapping
    public List<Film> listFilms() {
        return filmService.listFilms();
    }

    //обновление фильма
    @PutMapping
    public Film updateFilm(@RequestBody Film newFilm) {
        return filmService.updateFilm(newFilm);
    }

    //найти фильм по айди
    @GetMapping("/{id}")
    public Optional<Film> findFilmById(@PathVariable int id) {
        return filmService.findFilmById(id);
    }

    //добавить лайк
    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable int id, @PathVariable int userId) {
        filmService.addLike(id, userId);
    }

    //удалить лайк
    @DeleteMapping("{id}/like/{userId}")
    public void deleteLike(@PathVariable int id, @PathVariable int userId) {
        filmService.deleteLike(id, userId);
    }

    //по лайкам
    @GetMapping("/popular")
    public List<Film> mostPopularFilm(@RequestParam(defaultValue = "10") int count) {
        return filmService.mostPopularFilm(count);
    }
}

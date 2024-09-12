package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import java.util.List;

@RestController
@RequestMapping("/films")
@Slf4j
@AllArgsConstructor
public class FilmController {

    private FilmService filmService;

    /**
     * добавление фильма
     * @param film
     * @return объект класса Film
     */
    @PostMapping
    public Film createFilm(@RequestBody Film film) {
        return filmService.createFilm(film);
    }

    /**
     * получение списка всех добавленных фильмов
     * @return список всех фильмов
     */
    @GetMapping
    public List<Film> listFilms() {
        return filmService.listFilms();
    }

    /**
     * обновление фильма
     * @param newFilm
     * @return объект класса Film
     * @throws ru.yandex.practicum.filmorate.exception.NotFoundException если данный фильм не найден
     * @throws ru.yandex.practicum.filmorate.exception.ConditionsNotMetException если не указан идентификатор фильма
     */
    @PutMapping
    public Film updateFilm(@RequestBody Film newFilm) {
        return filmService.updateFilm(newFilm);
    }

    /**
     * найти фильм по id
     * @param id идентификатор фильма
     * @return найденный фильм
     * @throws ru.yandex.practicum.filmorate.exception.NotFoundException при отсуствии данного фильма
     */
    @GetMapping("/{id}")
    public Film findFilmById(@PathVariable int id) {
        return filmService.findFilmById(id);
    }

    /**
     * добавить лайк
     * @param id - идентификатор фильма, которомудобавляют лайк
     * @param userId - идентификатор пользователя, ставящего лайк
     * @throws ru.yandex.practicum.filmorate.exception.NotFoundException если фильм или пользователь не найдены
     */
    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable int id, @PathVariable int userId) {
        filmService.addLike(id, userId);
    }

    /**
     * удалить лайк
     * @param id - идентификатор фильма, у которго удаляем лайк
     * @param userId - идентификатор пользователя, чей лайк удаляем
     * @throws ru.yandex.practicum.filmorate.exception.NotFoundException если фильм или пользователь не найдены
     */
    @DeleteMapping("{id}/like/{userId}")
    public void deleteLike(@PathVariable int id, @PathVariable int userId) {
        filmService.deleteLike(id, userId);
    }

    /**
     * вывод списка наиболее популярных фильмов по лайкам
     * @param count - количество наиболее популярных фильмов
     * @return список популярных фильмов
     */
    @GetMapping("/popular")
    public List<Film> mostPopularFilm(@RequestParam(defaultValue = "10") int count) {
        return filmService.mostPopularFilm(count);
    }
}

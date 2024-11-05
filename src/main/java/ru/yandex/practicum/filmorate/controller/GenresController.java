package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.List;

@RestController
@RequestMapping("/genres")
@Slf4j
@RequiredArgsConstructor
public class GenresController {
    GenreService genreService;

    /**
     * получение спика всех жанров
     * @return список жанров
     */
    @GetMapping
    public List<Genre> getAllGenres() {
        return genreService.getAllGenres();
    }

    /**
     * получение жанра по идентификтаору
     * @param genreId
     * @return жанр
     */
    @GetMapping("/{id}")
    public Genre findGenreById(long genreId) {
        return genreService.findGenreById(genreId);
    }
}

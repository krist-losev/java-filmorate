package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.dal.GenreDdStorage;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class GenreService {

    private GenreDdStorage genreDdStorage;

    public List<Genre> getAllGenres() {
        return genreDdStorage.listGenres();
    }

    public Genre findGenreById(long genreId) {
        return genreDdStorage.findGenreById(genreId).orElseThrow(() ->
                new NotFoundException(("Пользователь с данным id найден.")));
    }

}

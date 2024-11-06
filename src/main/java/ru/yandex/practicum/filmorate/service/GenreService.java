package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.dal.GenreDdStorage;

import java.util.List;

@Service
@Slf4j
public class GenreService {

    private final GenreDdStorage genreDdStorage;

    public GenreService(GenreDdStorage genreDdStorage) {
        this.genreDdStorage = genreDdStorage;
    }

    public List<Genre> getAllGenres() {
        return genreDdStorage.listGenres();
    }

    public Genre findGenreById(int genreId) {
        return genreDdStorage.findGenreById(genreId).orElseThrow(() ->
                new NotFoundException("Жанр не найден."));
    }

}

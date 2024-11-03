package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.GenreDto;
import ru.yandex.practicum.filmorate.mappers.GenreMapper;
import ru.yandex.practicum.filmorate.storage.dal.GenreDdStorage;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class GenreService {

    private GenreDdStorage genreDdStorage;

    public List<GenreDto> getAllGenres() {
        return genreDdStorage.listGenres().stream().map(GenreMapper::mapToGenreDto).toList();
    }

    public GenreDto findGenreById(int genreId) {
        return genreDdStorage.findGenreById(genreId).map(GenreMapper::mapToGenreDto).get();
    }

}

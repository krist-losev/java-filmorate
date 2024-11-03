package ru.yandex.practicum.filmorate.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Builder
public class FilmDto {

    private int id;
    private String name;
    private String description;
    private String releaseDate;
    private long duration;
    private Set<Integer> like;
    private MpaDto mpa;
    private List<GenreDto> genres;
}

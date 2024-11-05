package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class Film {

    private Long id;
    private String name;
    private String description;
    private String releaseDate;
    private int duration;
    private Mpa mpa;
    private Set<Genre> genres;
}

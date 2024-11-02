package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Builder
public class Film {

    private int id;
    private String name;
    private String description;
    private String releaseDate;
    private long duration;
    private Set<Integer> like;
    private Mpa mpa;
    private List<Genre> genres;
}

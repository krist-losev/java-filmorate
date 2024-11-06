package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Film {

    private Long id;
    private String name;
    private String description;
    private String releaseDate;
    private int duration;
    private Mpa mpa;
    private Set<Genre> genres = new HashSet<>();
}

package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import java.util.Set;

@Data
@Builder
public class User {

    private int id;
    private String email;
    private String login;
    private String name;
    private String birthday;
    private Set<Integer> friends;
}

package ru.yandex.practicum.filmorate.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class UserDto {

    private int id;
    private String email;
    private String login;
    private String name;
    private String birthday;
    private Set<Integer> friends;
}

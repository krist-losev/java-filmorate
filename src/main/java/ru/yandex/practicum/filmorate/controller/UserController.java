package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validator.UserValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final Map<Integer, User> users = new HashMap<>();
    private UserValidator validator = new UserValidator();

    private int userId = 1;

    //создание пользователя
    @PostMapping
    public User createUser(@RequestBody User user) {
        log.info("Пришёл запрос на создание пользователя с email: " + user.getEmail());
        validator.validate(user);
        user.setId(userId++);
        users.put(user.getId(), user);
        return user;
    }

    //получение списка пользователей
    @GetMapping
    public List<User> listUsers() {
        return new ArrayList<>(users.values());
    }

    //обновление пользователя
    @PutMapping
    public User updateUser(@RequestBody User newUser) {
        log.info("Пришёл запрос на обновление пользователя");
        validator.validate(newUser);
        if (newUser.getId() == 0) {
            log.error("Id не может быть равен 0");
            throw new ConditionsNotMetException("Не указан id новой пользователя.");
        } else {
            if (users.containsKey(newUser.getId())) {
                User old = users.get(newUser.getId());
                old.setEmail(newUser.getEmail());
                old.setLogin(newUser.getLogin());
                old.setName(newUser.getName());
                old.setBirthday(newUser.getBirthday());
                return old;
            } else {
                log.error("Пользователь с id" + newUser.getId() + " не найден.");
                throw new NotFoundException("Пользователь не найден.");
            }
        }
    }
}

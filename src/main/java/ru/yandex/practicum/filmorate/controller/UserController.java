package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.*;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@Slf4j
public class UserController {

    private UserService userService;

    //создание пользователя
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    //получение списка пользователей
    @GetMapping
    public List<User> listUsers() {
        return userService.listUsers();
    }

    //обновление пользователя
    @PutMapping
    public User updateUser(@RequestBody User newUser) {
        return userService.updateUser(newUser);
    }

    //поиск пользователя по id
    @GetMapping("/{postId}")
    public Optional<User> findUserById(@PathVariable int postId) {
        return userService.findUserById(postId);
    }

    //добавление пользователя в друзья
    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable int id, @PathVariable int friendId) {
        userService.addFriend(id, friendId);
    }

    //удаление из друзей
    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable int id, @PathVariable int friendId) {
        userService.deleteFriend(id, friendId);
    }

    //друзья
    @GetMapping("/{id}/friends")
    public List<User> getFriendsList(@PathVariable int id) {
        return userService.getFriendsList(id);
    }

    //общие друзья
    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> listCommonsFriends(@PathVariable int id, @PathVariable int otherId) {
        return userService.listCommonsFriends(id, otherId);
    }
}

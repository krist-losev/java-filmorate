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

    /**
     * создание пользователя
     * @param user - пользователь, которого необхдимо создать
     * @return - созданный пользователь
     */
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    /**
     * получение списка пользователей
     * @return список всех созданных пользователей
     */
    @GetMapping
    public List<User> listUsers() {
        return userService.listUsers();
    }

    /**
     * обновление пользователя
     * @param newUser - новые даные пользователя
     * @return - нового пользователя
     * @throws ru.yandex.practicum.filmorate.exception.NotFoundException при отсутствии данного пользователя
     */
    @PutMapping
    public User updateUser(@RequestBody User newUser) {
        return userService.updateUser(newUser);
    }

    /**
     * поиск пользователя по id
     * @param postId - идентификатор искомого пользователя
     * @return найденный пользователь
     * @throws ru.yandex.practicum.filmorate.exception.NotFoundException при отсутствии данного пользователя
     */
    @GetMapping("/{postId}")
    public User findUserById(@PathVariable int postId) {
        return userService.findUserById(postId);
    }

    /**
     * добавление пользователя в друзья
     * @param id - идентификтаор пользователя, к которому добавляют в друзья
     * @param friendId - идентификтаор пользователя, который добавляется в друзья
     */
    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable int id, @PathVariable int friendId) {
        userService.addFriend(id, friendId);
    }

    /**
     * удаление из друзей
     * @param id - идентификатор пользователя, у которого удаляют друга
     * @param friendId - идентификтаор удаляемого друга
     * @throws ru.yandex.practicum.filmorate.exception.ConditionsNotMetException если список друзей пользователей пуст
     */
    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable int id, @PathVariable int friendId) {
        userService.deleteFriend(id, friendId);
    }

    /**
     * получение списка друзей конкретного пользователя
     * @param id - идентификатор пользователя, чей спиок друзей необходимо получить
     * @return список друзей
     * @throws ru.yandex.practicum.filmorate.exception.ConditionsNotMetException при пустом списке друзей пользователя
     */
    @GetMapping("/{id}/friends")
    public List<User> getFriendsList(@PathVariable int id) {
        return userService.getFriendsList(id);
    }

    /**
     * получение списка общих друзей двух пользователей
     * @param id - идентификтаор одного пользователя
     * @param otherId - идентификтаор другого пользователя
     * @return список общих друзей переданных пользователей
     */
    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> listCommonsFriends(@PathVariable int id, @PathVariable int otherId) {
        return userService.listCommonsFriends(id, otherId);
    }
}

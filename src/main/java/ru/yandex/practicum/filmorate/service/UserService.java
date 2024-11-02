package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.dal.UserDbStorage;

import java.util.*;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private UserDbStorage userDbStorage;

    public void addFriend(int userId, int friendId) {
        log.info("Поступил запрос на добавление юзера с id " + friendId + " в друзья.");
        User user = userDbStorage.findUserById(userId).orElseThrow(() ->
                new NotFoundException(("Пользователь с данным id найден.")));
        User friend = userDbStorage.findUserById(friendId).orElseThrow(() ->
                new NotFoundException(("Пользователь с данным id найден.")));
        userDbStorage.addFriend(userId, friendId);
    }

    public void deleteFriend(int userId, int friendId) {
        log.info("Поступил запрос на удаление пользователя из списка друзей");
        User user = userDbStorage.findUserById(userId).orElseThrow(() ->
                new NotFoundException(("Пользователь с данным id найден.")));
        if (user.getFriends() == null) {
            throw new ConditionsNotMetException("Список друзей пользователя с id " + userId + " пуст.");
        }
        User friend = userDbStorage.findUserById(friendId).orElseThrow(() ->
                new NotFoundException(("Пользователь с данным id найден.")));
        if (friend.getFriends() == null) {
            throw new ConditionsNotMetException("Список друзей пользователя с id " + friendId + " пуст.");
        }
        userDbStorage.deletedFriend(userId, friendId);
    }

    public List<User> getFriendsList(int userId) {
        User user = userDbStorage.findUserById(userId).orElseThrow(() ->
                new NotFoundException(("Пользователь с данным id найден.")));
        if (user.getFriends() == null) {
            throw new ConditionsNotMetException("Список друзей пользователя с id " + userId + " пуст.");
        }
        return userDbStorage.friendsList(userId);
    }

    public List<User> listCommonsFriends(int userId, int otherId) {
        User user = userDbStorage.findUserById(userId).orElseThrow(() ->
                new NotFoundException(("Пользователь с данным id найден.")));
        User other = userDbStorage.findUserById(otherId).orElseThrow(() ->
                new NotFoundException(("Пользователь с данным id найден.")));
        return userDbStorage.commonFriendsList(userId, otherId);
    }

    public void getFriendById(int userId, int friendId) {
        User user = userDbStorage.findUserById(userId)
                .orElseThrow(() -> new NotFoundException(("Пользователь с данным id найден.")));
        User friend = userDbStorage.findUserById(friendId)
                .orElseThrow(() -> new NotFoundException(("Пользователь с данным id найден.")));
        if (!user.getFriends().contains(friendId)) {
            throw new NotFoundException(("Пользователь с данным id найден."));
        }
        log.info("Пользователь {} получил информацию о друге-пользователе {}", user.getName(), friend.getName());
    }

    public User createUser(User user) {
        return userDbStorage.createUser(user);
    }

    public List<User> listUsers() {
        return userDbStorage.listUsers();
    }

    public User updateUser(User newUser) {
        return userDbStorage.updateUser(newUser);
    }

    public User findUserById(int userId) {
        return userDbStorage.findUserById(userId)
                .orElseThrow(() -> new NotFoundException(("Пользователь с данным id найден.")));
    }
}

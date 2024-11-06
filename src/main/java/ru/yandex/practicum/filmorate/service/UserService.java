package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.dal.FriendsDdStorage;
import ru.yandex.practicum.filmorate.storage.dal.UserDbStorage;

import java.util.*;

@Service
@Slf4j
public class UserService {

    private final UserDbStorage userDbStorage;
    private final FriendsDdStorage friendsDdStorage;

    public UserService(UserDbStorage userDbStorage, FriendsDdStorage friendsDdStorage) {
        this.userDbStorage = userDbStorage;
        this.friendsDdStorage = friendsDdStorage;
    }

    public void addFriend(long userId, long friendId) {
        log.info("Поступил запрос на добавление юзера с id " + friendId + " в друзья.");
        userDbStorage.findUserById(userId).orElseThrow(() ->
                new NotFoundException(("Пользователь с данным id не найден.")));
        userDbStorage.findUserById(friendId).orElseThrow(() ->
                new NotFoundException(("Пользователь с данным id не найден.")));
        friendsDdStorage.addFriend(userId, friendId);
        log.info("Пользователь с id {} добавлен в друзья пользователя {}", userId, friendId);
    }

    public void deleteFriend(long userId, long friendId) {
        log.info("Поступил запрос на удаление пользователя из списка друзей");
        userDbStorage.findUserById(userId).orElseThrow(() ->
                new NotFoundException(("Пользователь с данным id не найден.")));
        userDbStorage.findUserById(friendId).orElseThrow(() ->
                new NotFoundException(("Пользователь с данным id не найден.")));
        List<User> friends = getFriendsList(userId);
        if (friends.contains(userDbStorage.findUserById(friendId).get())) {
            friendsDdStorage.deletedFriend(userId, friendId);
        }
    }

    public List<User> getFriendsList(long userId) {
        userDbStorage.findUserById(userId).orElseThrow(() ->
                new NotFoundException(("Пользователь с данным id не найден.")));
        return friendsDdStorage.friendsList(userId);

    }

    public List<User> listCommonsFriends(long userId, long otherId) {
        userDbStorage.findUserById(userId).orElseThrow(() ->
                new NotFoundException(("Пользователь с данным id не найден.")));
        userDbStorage.findUserById(otherId).orElseThrow(() ->
                new NotFoundException(("Пользователь с данным id не найден.")));
        List<User> common = friendsDdStorage.commonFriendsList(userId, otherId);
        if (!common.isEmpty()) {
            return common;
        } else {
            log.error("Пользователи с id {} и {} не имеют общих друзей.", userId, otherId);
            throw new NotFoundException("Пользователи не имеют общих друзей.");
        }
    }

    public User createUser(User user) {
        log.info("Пришёл запрос на создание пользователя с email: " + user.getEmail());
        return userDbStorage.createUser(user);
    }

    public List<User> listUsers() {
        return userDbStorage.listUsers();
    }

    public User updateUser(User newUser) {
        if (newUser.getId() == 0) {
            throw new RuntimeException("Id должен быть указан");
        }
        findUserById(newUser.getId());
        return userDbStorage.updateUser(newUser);
    }

    public User findUserById(long userId) {
        return userDbStorage.findUserById(userId).orElseThrow(() ->
                new NotFoundException(("Пользователь с данным id не найден.")));
    }
}

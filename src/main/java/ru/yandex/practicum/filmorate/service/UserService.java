package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private UserStorage userStorage;

    public void addFriend(int userId, int friendId) {
        log.info("Поступил запрос на добавление юзера с id " + friendId + " в друзья.");
        User user = findUserById(userId);
        User friend = findUserById(friendId);
        user.getFriends().add(friendId);
        friend.getFriends().add(userId);
    }

    public void deleteFriend(int userId, int friendId) {
        log.info("Поступил запрос на удаление пользователя из списка друзей");
        User user = findUserById(userId);
        if (user.getFriends() == null) {
            throw new ConditionsNotMetException("Список друзей пользователя с id " + userId + " пуст.");
        }
        User friend = findUserById(friendId);
        if (friend.getFriends() == null) {
            throw new ConditionsNotMetException("Список друзей пользователя с id " + friendId + " пуст.");
        }
        user.getFriends().remove(friendId);
        friend.getFriends().remove(userId);
    }

    public List<User> getFriendsList(int userId) {
        User user = findUserById(userId);
        if (user.getFriends() == null) {
            throw new ConditionsNotMetException("Список друзей пользователя с id " + userId + " пуст.");
        }
        return user.getFriends().stream()
                .map(this::findUserById)
                .collect(Collectors.toList());
    }

    public List<User> listCommonsFriends(int userId, int otherId) {
        Set<Integer> user1ListFriends = findUserById(userId).getFriends();
        Set<Integer> otherListFriends = findUserById(otherId).getFriends();
        Set<Integer> commonFriends = new HashSet<>(user1ListFriends);
        commonFriends.retainAll(otherListFriends);
        return commonFriends.stream()
                .map(this::findUserById)
                .collect(Collectors.toList());
    }

    public User createUser(User user) {
        return userStorage.createUser(user);
    }

    public List<User> listUsers() {
        return userStorage.listUsers();
    }

    public User updateUser(User newUser) {
        return userStorage.updateUser(newUser);
    }

    public User findUserById(int userId) {
        return userStorage.findUserById(userId)
                .orElseThrow(() -> new NotFoundException(("Пользователь с данным id найден.")));
    }
}

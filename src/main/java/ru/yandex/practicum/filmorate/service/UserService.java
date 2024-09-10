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

    //как добавление в друзья
    public void addFriend(int userId, int friendId) {
        log.info("Поступил запрос на добавление юзера с id " + friendId + " в друзья.");
        User user = findUserById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь " + userId + " не найден."));
        User friend = findUserById(friendId)
                .orElseThrow(() -> new NotFoundException("Пользователь " + friendId + " не найден."));
        user.getFriends().add(friendId);
        friend.getFriends().add(userId);
    }

    //удаление из друзей
    public void deleteFriend(int userId, int friendId) {
        log.info("Поступил запрос на удаление пользователя из списка друзей");
        User user = findUserById(userId).orElseThrow(() -> new NotFoundException("пользователь не найден."));
        if (user.getFriends() == null) {
            throw new ConditionsNotMetException("Список друзей пользователя с id " + userId + " пуст.");
        }
        User friend = findUserById(friendId).orElseThrow(() -> new NotFoundException("Пользователь не найден."));
        if (friend.getFriends() == null) {
            throw new ConditionsNotMetException("Список друзей пользователя с id " + friendId + " пуст.");
        }
        user.getFriends().remove(friendId);
        friend.getFriends().remove(userId);
    }

    // вывод списка друзей
    public List<User> getFriendsList(int userId) {
        User user = findUserById(userId).orElseThrow(() -> new NotFoundException("Пользователь не найден."));
        if (user.getFriends() == null) {
            throw new ConditionsNotMetException("Список друзей пользователя с id " + userId + " пуст.");
        }
        return user.getFriends().stream()
                .map(id -> findUserById(id).orElseThrow(() -> new NotFoundException("Пользователь не найден.")))
                .collect(Collectors.toList());
    }

    //список друзей общих с другим пользователем
    public List<User> listCommonsFriends(int userId, int otherId) {
        Set<Integer> user1ListFriends = findUserById(userId)
                .orElseThrow(() -> new NotFoundException("пользователь не найден.")).getFriends();
        Set<Integer> otherListFriends = findUserById(otherId)
                .orElseThrow(() -> new NotFoundException("пользователь не найден.")).getFriends();
        Set<Integer> commonFriends = new HashSet<>(user1ListFriends);
        commonFriends.retainAll(otherListFriends);
        return commonFriends.stream()
                .map(id -> findUserById(id).orElseThrow(() -> new NotFoundException("Пользователь с таким id не найден")))
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

    public Optional<User> findUserById(int userId) {
        return userStorage.findUserById(userId);
    }
}

package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.UserDto;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.mappers.UserMapper;
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

    public List<UserDto> getFriendsList(int userId) {
        User user = userDbStorage.findUserById(userId).orElseThrow(() ->
                new NotFoundException(("Пользователь с данным id найден.")));
        if (user.getFriends() == null) {
            throw new ConditionsNotMetException("Список друзей пользователя с id " + userId + " пуст.");
        }
        return userDbStorage.friendsList(userId).stream().map(UserMapper::mapToUserDto).toList();
    }

    public List<UserDto> listCommonsFriends(int userId, int otherId) {
        User user = userDbStorage.findUserById(userId).orElseThrow(() ->
                new NotFoundException(("Пользователь с данным id найден.")));
        User other = userDbStorage.findUserById(otherId).orElseThrow(() ->
                new NotFoundException(("Пользователь с данным id найден.")));
        return userDbStorage.commonFriendsList(userId, otherId).stream().map(UserMapper::mapToUserDto).toList();
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

    public UserDto createUser(User user) {
        Optional<User> newUser = Optional.of(userDbStorage.createUser(user));
        return newUser.map(UserMapper::mapToUserDto).get();
    }

    public List<UserDto> listUsers() {
        return userDbStorage.listUsers().stream().map(UserMapper::mapToUserDto).toList();
    }

    public UserDto updateUser(User newUser) {
        if (newUser.getId() == 0) {
            throw new RuntimeException("Id олжен быть указан");
        }
        Optional<User> user = Optional.of(userDbStorage.updateUser(newUser));
        return user.map(UserMapper::mapToUserDto).get();
    }

    public UserDto findUserById(int userId) {
        return userDbStorage.findUserById(userId).map(UserMapper::mapToUserDto)
                .orElseThrow(() -> new NotFoundException(("Пользователь с данным id найден.")));
    }
}

package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

public interface UserStorage {

    User createUser(User user);

    List<User> listUsers();

    User updateUser(User newUser);

    Optional<User> findUserById(int userId);
}

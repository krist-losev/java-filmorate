package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validator.UserValidator;

import java.util.*;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {

    private final Map<Integer, User> users = new HashMap<>();
    private UserValidator validator = new UserValidator();

    private int userId = 1;

    @Override
    public User createUser(User user) {
        log.info("Пришёл запрос на создание пользователя с email: " + user.getEmail());
        validator.validate(user);
        user.setId(userId++);
        user.setFriends(new HashSet<>());
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public List<User> listUsers() {
        return new ArrayList<>(users.values());
    }

    //обновление пользователя
    @Override
    public User updateUser(User newUser) {
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

    @Override
    public Optional<User> findUserById(int userId) {
        log.info("Пришёл запрос на поиск пользователя");
        return Optional.of(users.values().stream()
                .filter(user -> user.getId() == userId)
                .findFirst())
                .get();
    }
}

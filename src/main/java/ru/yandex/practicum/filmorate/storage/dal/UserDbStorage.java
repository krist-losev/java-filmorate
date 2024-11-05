package ru.yandex.practicum.filmorate.storage.dal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.interfaces.UserStorage;
import ru.yandex.practicum.filmorate.validator.UserValidator;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDbStorage extends BaseDdStorage<User> implements UserStorage {

    private final UserValidator validator = new UserValidator();
    private static final String FIND_ALL_USERS = "SELECT * FROM users";
    private static final String FIND_USER_BY_ID_QUERY = "SELECT * FROM users WHERE id = ?";
    private static final String INSERT_USER_QUERY = "INSERT INTO users (login, name, email, birthday)"
            + "VALUES (?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE users SET login = ?, name = ?, email = ?, birthday = ?"
            + " WHERE id = ?";

    @Autowired
    public UserDbStorage(JdbcTemplate jdbc, RowMapper<User> mapper) {
        super(jdbc, mapper);
    }

    @Override
    public User createUser(User user) {
        validator.validate(user);
        long id = insert(
                INSERT_USER_QUERY,
                user.getLogin(),
                user.getName(),
                user.getEmail(),
                user.getBirthday());
        user.setId(id);
        return user;
    }

    @Override
    public List<User> listUsers() {
        return findMany(FIND_ALL_USERS);
    }

    @Override
    public User updateUser(User newUser) {
        validator.validate(newUser);
        Optional<User> user = findOne(FIND_USER_BY_ID_QUERY, newUser.getId());
        if (user.isPresent()) {
            update(UPDATE_QUERY,
                    newUser.getEmail(),
                    newUser.getLogin(),
                    newUser.getName(),
                    newUser.getBirthday(),
                    newUser.getId());
        } else {
            throw new NotFoundException("Пользователь не найден.");
        }
        return newUser;
    }

    @Override
    public Optional<User> findUserById(long userId) {
        return findOne(FIND_USER_BY_ID_QUERY, userId);
    }

}

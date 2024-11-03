package ru.yandex.practicum.filmorate.storage.dal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.validator.UserValidator;

import java.util.List;
import java.util.Optional;

@Repository("userDbStorage")
public class UserDbStorage extends BaseDdStorage<User> implements UserStorage {
    private final UserValidator validator = new UserValidator();

    private static final String FIND_ALL_QUERY = "SELECT * FROM users";
    private static final String FIND_USER_BY_ID_QUERY = "SELECT * FROM users WHERE id = ?";
    private static final String INSERT_USER_QUERY = "INSERT INTO users (email, login, name, birthday) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE users SET email = ?, login = ?, name = ?, birthday = ?"
    + " WHERE id = ?";
    private static final String ADD_FRIEND_QUERY = "INSERT INTO friends (id_user, id_friends) VALUES (?, ?)";
    private static final String DELETE_FRIEND_ID = "DELETE FROM friends WHERE id_user = ? AND id_friend = ?";
    private static final String GET_ALL_FRIENDS = "SELECT us.* FROM friends AS fr LEFT JOIN users AS us"
    + " ON fr.id_friend = us.id WHERE fr.id_user = ?";
    private static final String GET_ALL_COMMON_FRIENDS = "SELECT fr1.friend_id FROM friends AS fr1 "
            + "INNER JOIN friends AS fr2 ON fr1.friend_id = fr2.friend_id WHERE fr1.user_id = ? AND fr2.user_id = ?";

    @Autowired
    public UserDbStorage(JdbcTemplate jdbc, RowMapper<User> mapper) {
        super(jdbc, mapper);
    }

    @Override
    public User createUser(User user) {
        validator.validate(user);
        int id = (int) insert(
                INSERT_USER_QUERY,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday());
        user.setId(id);
        return user;
    }

    @Override
    public List<User> listUsers() {
        return findMany(FIND_ALL_QUERY);
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
    public Optional<User> findUserById(int userId) {
        return findOne(FIND_USER_BY_ID_QUERY, userId);
    }

    public void addFriend(int userId, int friendId) {
        update(ADD_FRIEND_QUERY, userId, friendId);
    }

    public void deletedFriend(int userId, int friendId) {
        update(DELETE_FRIEND_ID, userId, friendId);
    }

    public List<User> friendsList(int id) {
        return findMany(GET_ALL_FRIENDS, id);
    }

    public List<User> commonFriendsList(int user1, int user2) {
        return findMany(GET_ALL_COMMON_FRIENDS, user1, user2);
    }

}

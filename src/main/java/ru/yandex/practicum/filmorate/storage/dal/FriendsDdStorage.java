package ru.yandex.practicum.filmorate.storage.dal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

@Repository
public class FriendsDdStorage extends BaseDdStorage<User> {

    private static final String ADD_FRIEND_QUERY = "INSERT INTO friends (id_user, id_friend) VALUES (?, ?)";
    private static final String DELETE_FRIEND_ID = "DELETE FROM friends WHERE id_user = ? AND id_friend = ?";
    private static final String GET_ALL_FRIENDS = "SELECT * FROM users WHERE id IN (SELECT id_friend FROM friends "
            + "WHERE id_user = ?";
    private static final String GET_ALL_COMMON_FRIENDS = "SELECT * FROM users WHERE id IN " +
            "(SELECT id_friend FROM friends WHERE id_user = ?) AND id IN " +
            "(SELECT id_friend FROM friendship WHERE id_user = ?)";

    public FriendsDdStorage(JdbcTemplate jdbc, RowMapper<User> mapper) {
        super(jdbc, mapper);
    }

    public void addFriend(long userId, long friendId) {
        insertKeys(ADD_FRIEND_QUERY, userId, friendId);
    }

    public void deletedFriend(long userId, long friendId) {
       delete(DELETE_FRIEND_ID, userId, friendId);
    }

    public List<User> friendsList(long id) {
        return findMany(GET_ALL_FRIENDS, id);
    }

    public List<User> commonFriendsList(long user1, long user2) {
        return findMany(GET_ALL_COMMON_FRIENDS, user1, user2);
    }
}

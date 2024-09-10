package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.ValidException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.HashSet;

@SpringBootTest
public class UserValidateTest {
    UserController userController = new UserController(new UserService(new InMemoryUserStorage()));

    @Test
    void createUserTest() {
        User user = User.builder()
                .email("aaa@ry")
                .login("nnn")
                .name("nn")
                .birthday("1999-01-18")
                .build();
        userController.createUser(user);

        Assertions.assertEquals(1, userController.listUsers().size());
    }

    @Test
    void createUserEmailNullTest() {
        User user = User.builder()
                .email("")
                .login("nnn")
                .name("nn")
                .birthday("1999-01-18")
                .build();;

        Assertions.assertThrowsExactly(ValidException.class, () ->  userController.createUser(user));
    }

    @Test
    void createUserWithWrongEmailTest() {
        User user = User.builder()
                .email("aaary")
                .login("nnn")
                .name("nn")
                .birthday("1999-01-18")
                .build();

        Assertions.assertThrowsExactly(ValidException.class, () ->  userController.createUser(user));
    }

    @Test
    void createUserWithLoginNullTest() {
        User user = User.builder()
                .email("aaa@ry")
                .login("")
                .name("nn")
                .birthday("1999-01-18")
                .build();

        Assertions.assertThrowsExactly(ValidException.class, () ->  userController.createUser(user));
    }

    @Test
    void createUserWithNameNullTest() {
        User user = User.builder()
                .email("aaa@ry")
                .login("nnn")
                .name("")
                .birthday("1999-01-18")
                .build();
        userController.createUser(user);

        Assertions.assertEquals(user.getName(), user.getLogin());
    }

    @Test
    void createUserWithBirthdayInFutureTest() {
        User user = User.builder()
                .email("aaa@ry")
                .login("nnn")
                .name("nn")
                .birthday("2025-01-18")
                .build();

        Assertions.assertThrowsExactly(ValidException.class, () ->  userController.createUser(user));
    }

    @Test
    void addFriendsTest() {
        User user = User.builder()
                .email("aaa@ry")
                .login("nnn")
                .name("nn")
                .birthday("2000-01-18")
                .build();

        User userOther = User.builder()
                .email("bba@ry")
                .login("mmm")
                .name("mm")
                .birthday("2012-01-18")
                .build();

        userController.createUser(user);
        Assertions.assertEquals(0, user.getFriends().size());
        userController.createUser(userOther);
        userController.addFriend(user.getId(), userOther.getId());

        Assertions.assertEquals(1, user.getFriends().size());
        Assertions.assertEquals(1, userOther.getFriends().size());
    }
}

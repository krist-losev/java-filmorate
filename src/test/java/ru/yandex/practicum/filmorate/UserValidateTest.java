package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.ValidException;
import ru.yandex.practicum.filmorate.model.User;

@SpringBootTest
public class UserValidateTest {

    UserController userController = new UserController();

    @Test
    void createUserTest() {
        User user = new User(1, "aaa@ry", "nnn", "nn", "1999-01-18");
        userController.createUser(user);

        Assertions.assertEquals(1, userController.listUsers().size());
    }

    @Test
    void createUserEmailNullTest(){
        User user = new User(1, "", "nnn", "nn", "1999-01-18");

        Assertions.assertThrowsExactly(ValidException.class, () ->  userController.createUser(user));
    }

    @Test
    void createUserWithWrongEmailTest() {
        User user = new User(1, "aaary", "nnn", "nn", "1999-01-18");

        Assertions.assertThrowsExactly(ValidException.class, () ->  userController.createUser(user));
    }

    @Test
    void createUserWithLoginNullTest() {
        User user = new User(1, "aaa@ry", "", "nn", "1999-01-18");

        Assertions.assertThrowsExactly(ValidException.class, () ->  userController.createUser(user));
    }

    @Test
    void createUserWithNameNullTest() {
        User user = new User(1, "aaa@ry", "nnn", "", "1999-01-18");
        userController.createUser(user);

        Assertions.assertEquals(user.getName(), user.getLogin());
    }

    @Test
    void createUserWithBirthdayInFutureTest() {
        User user = new User(1, "aaa@ry", "nnn", "nn", "2025-01-18");

        Assertions.assertThrowsExactly(ValidException.class, () ->  userController.createUser(user));
    }

}

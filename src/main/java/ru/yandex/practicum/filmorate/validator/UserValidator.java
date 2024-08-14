package ru.yandex.practicum.filmorate.validator;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.ValidException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
public class UserValidator {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public void validate(User user) {
        //email не должен быть пустым и должен содержать @
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            log.error("Поле email не должно быть пустым.");
            throw new ValidException("Неверный email.");
        } else if (user.getEmail().indexOf("@") == -1) {
            log.error("Email должен содержать '@'.");
            throw new ValidException(("Неверный email."));
        }
        //логин не пуст и без пробелов
        if (user.getLogin() == null || user.getLogin().isBlank() || user.getLogin().indexOf(" ") != -1) {
            log.error("Логин пуст или содержит пробелы.");
            throw new ValidException("Неверный логин.");
        }
        //если имя пусто, то им становится логин
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        //дата рождения не может быть будущим
        if (LocalDate.parse(user.getBirthday(), FORMATTER).isAfter(LocalDate.now())) {
            log.error("Дата рождения не может быть будущим.");
            throw new ValidException("Неверная дата рождения.");
        }
    }
}

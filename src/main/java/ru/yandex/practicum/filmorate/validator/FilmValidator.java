package ru.yandex.practicum.filmorate.validator;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.ValidException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
public class FilmValidator {

    private static final LocalDate BIRTHDAY_FILM = LocalDate.of(1895, 12, 28);
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final int MAX_LENGTH = 200;

    public void validate(Film film) {
        //название не может быть пустым
        if (film.getName() == null || film.getName().isBlank()) {
            log.error("Пустое поле 'Название фильма'");
            throw new ValidException("Неверное название фильма");
        }
        //максимальная длина описания — 200 символов
        if (film.getDescription().length() > MAX_LENGTH) {
            log.error("Превышена максимальная длина описания. Лимит - 200.");
            throw new ValidException("Неверная длина описания");
        }
        //дата релиза
        if (LocalDate.parse(film.getReleaseDate(), FORMATTER).isBefore(BIRTHDAY_FILM)) {
            log.error("Дата релиза не должна быть раньше " + BIRTHDAY_FILM.format(FORMATTER));
            throw new ValidException("Дата релиза неверна.");
        }
        //продолжительность фильма должна быть положительным числом
        if (film.getDuration() <= 0) {
            log.error("Продолжительность фильма должна быть больше нуля.");
            throw new ValidException("Неверная продолжительность фильма.");
        }
    }
}

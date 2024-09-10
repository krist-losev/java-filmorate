package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exception.ValidException;
import ru.yandex.practicum.filmorate.model.Film;

@SpringBootTest
public class FilmValidateTest {

    FilmController filmController = new FilmController();

    @Test
    void createFilmTest() {
        Film film = new Film(1, "mmm", "kkkk", "2000-12-01", 125);
        filmController.createFilm(film);

        Assertions.assertEquals(1, filmController.listFilms().size());
    }

    @Test
    void createFilmWithNameNullTest() {
        Film film = new Film(1, "", "kkkk", "2000-12-01", 125);

        Assertions.assertThrowsExactly(ValidException.class, () -> filmController.createFilm(film));
    }

    @Test
    void createFilmWithWrongReleaseDateTest() {
        Film film = new Film(1, "mm", "kkkk", "1654-12-01", 125);

        Assertions.assertThrowsExactly(ValidException.class, () -> filmController.createFilm(film));
    }

    @Test
    void createFilmWithWrongDurationTest() {
        Film film = new Film(1, "mm", "kkkk", "2005-12-01", -5);

        Assertions.assertThrowsExactly(ValidException.class, () -> filmController.createFilm(film));
    }
}

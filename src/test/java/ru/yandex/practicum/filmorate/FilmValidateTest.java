package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exception.ValidException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

@SpringBootTest
public class FilmValidateTest {

    private final FilmService filmService = new FilmService(new InMemoryFilmStorage(), new InMemoryUserStorage());
    private final FilmController filmController = new FilmController(filmService);

    @Test
    void createFilmTest() {
        Film film = Film.builder()
                .name("mmm")
                .description("kkkk")
                .releaseDate("2000-12-01")
                .duration(125)
                .build();
        filmController.createFilm(film);

        Assertions.assertEquals(1, filmController.listFilms().size());
    }

    @Test
    void createFilmWithNameNullTest() {
        Film film = Film.builder()
                .name("")
                .description("kkkk")
                .releaseDate("2000-12-01")
                .duration(125)
                .build();

        Assertions.assertThrowsExactly(ValidException.class, () -> filmController.createFilm(film));
    }

    @Test
    void createFilmWithWrongReleaseDateTest() {
        Film film = Film.builder()
                .name("bbb")
                .description("kkkk")
                .releaseDate("1654-12-01")
                .duration(125)
                .build();

        Assertions.assertThrowsExactly(ValidException.class, () -> filmController.createFilm(film));
    }

    @Test
    void createFilmWithWrongDurationTest() {
        Film film = Film.builder()
                .name("mmm")
                .description("kkkk")
                .releaseDate("2000-12-01")
                .duration(-5)
                .build();;

        Assertions.assertThrowsExactly(ValidException.class, () -> filmController.createFilm(film));
    }
}

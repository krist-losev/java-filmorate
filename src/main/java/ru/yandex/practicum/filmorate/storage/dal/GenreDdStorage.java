package ru.yandex.practicum.filmorate.storage.dal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class GenreDdStorage extends BaseDdStorage<Genre> {

    private static final String FIND_ALL_QUERY = "SELECT * FROM genres";
    private static final String FIND_GENRE_BY_ID_QUERY = "SELECT * FROM genres WHERE id = ?";

    private static final String ADD_GENRE_FILM = "INSERT INTO film_genre(film_id, genre_id) VALUES (?, ?)";
    private static final String FIND_ALL_GENRES_FILM = "SELECT g.id, g.genre_name FROM genres AS g " +
            "JOIN film_genre AS fg ON g.id = fg.genre_id WHERE fg.film_id = ?";
    private static final String DELETED_GENRE_TO_FILM = "DELETE FROM film_genre WHERE film_id = ?";


    @Autowired
    public GenreDdStorage(JdbcTemplate jdbc, RowMapper<Genre> mapper) {
        super(jdbc, mapper);
    }


    public List<Genre> listGenres() {
        return findMany(FIND_ALL_QUERY);
    }

    public Optional<Genre> findGenreById(long genreId) {
        return findOne(FIND_GENRE_BY_ID_QUERY, genreId);
    }

    public void addGenreToFilm(long filmId, long genreId) {
        insertKeys(ADD_GENRE_FILM, filmId, genreId);
    }

    public Set<Genre> addAllGenresToFilm(long filmId) {
        return new HashSet<>(findMany(FIND_ALL_GENRES_FILM, filmId));
    }

    public boolean deletedGenreFilm(Long idFilm) {
        return delete(DELETED_GENRE_TO_FILM, idFilm);
    }
}

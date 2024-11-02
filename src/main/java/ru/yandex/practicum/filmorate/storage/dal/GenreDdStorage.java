package ru.yandex.practicum.filmorate.storage.dal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Optional;

@Repository
public class GenreDdStorage extends BaseDdStorage<Genre> {

    private static final String FIND_ALL_QUERY = "SELECT * FROM genres";
    private static final String FIND_GENRE_BY_ID_QUERY = "SELECT * FROM genres WHERE id = ?";
    private static final String FIND_GENRE_BY_NAME_QUERY = "SELECT * FROM genres WHERE name = ?";
    private static final String INSERT_GENRE_QUERY = "INSERT INTO genres(name) VALUES (?)";

    @Autowired
    public GenreDdStorage(JdbcTemplate jdbc, RowMapper<Genre> mapper) {
        super(jdbc, mapper);
    }

    public Genre createGenre(Genre genre) {
        int id = (int) insert(
                INSERT_GENRE_QUERY,
                genre.getName());
        genre.setId(id);
        return genre;
    }

    public List<Genre> listGenres() {
        return findMany(FIND_ALL_QUERY);
    }

    public Optional<Genre> findGenreById(int genreId) {
        return findOne(FIND_GENRE_BY_ID_QUERY, genreId);
    }

    public Optional<Genre> findGenreByName(String name) {
        return findOne(FIND_GENRE_BY_NAME_QUERY, name);
    }
}

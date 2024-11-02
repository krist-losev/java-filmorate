package ru.yandex.practicum.filmorate.storage.dal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;
import java.util.Optional;

@Repository
public class MpaDdStorage extends BaseDdStorage<Mpa> {

    private static final String FIND_ALL_QUERY = "SELECT * FROM mpa";
    private static final String FIND_MPA_BY_ID_QUERY = "SELECT * FROM mpa WHERE id = ?";

    @Autowired
    public MpaDdStorage(JdbcTemplate jdbc, RowMapper<Mpa> mapper) {
        super(jdbc, mapper);
    }

    public List<Mpa> listMpa() {
        return findMany(FIND_ALL_QUERY);
    }

    public Optional<Mpa> findMpaById(int mpaId) {
        return findOne(FIND_MPA_BY_ID_QUERY, mpaId);
    }
}


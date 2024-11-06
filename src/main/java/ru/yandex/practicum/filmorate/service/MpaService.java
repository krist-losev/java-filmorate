package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.dal.MpaDdStorage;

import java.util.List;

@Service
@Slf4j
public class MpaService {
    private final MpaDdStorage mpaDdStorage;

    public MpaService(MpaDdStorage mpaDdStorage) {
        this.mpaDdStorage = mpaDdStorage;
    }

    public List<Mpa> listMpa() {
        return mpaDdStorage.listMpa();
    }

    public Mpa findMpaById(int mpaId) {
            return mpaDdStorage.findMpaById(mpaId).orElseThrow(() ->
                    new NotFoundException("Ограничение не найдено"));
    }
}

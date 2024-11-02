package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.dal.MpaDdStorage;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class MpaService {
    MpaDdStorage mpaDdStorage;

    public List<Mpa> listMpa() {
        return mpaDdStorage.listMpa();
    }

    public Mpa findMpaById(int mpaId) {
        return mpaDdStorage.findMpaById(mpaId).get();
    }
}

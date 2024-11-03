package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.MpaDto;
import ru.yandex.practicum.filmorate.mappers.MpaMapper;
import ru.yandex.practicum.filmorate.storage.dal.MpaDdStorage;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class MpaService {
    MpaDdStorage mpaDdStorage;

    public List<MpaDto> listMpa() {
        return mpaDdStorage.listMpa().stream().map(MpaMapper::mapToMpaDto).toList();
    }

    public MpaDto findMpaById(int mpaId) {
        return mpaDdStorage.findMpaById(mpaId).map(MpaMapper::mapToMpaDto).get();
    }
}

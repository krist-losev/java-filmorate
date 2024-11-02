package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.List;

@RestController
@RequestMapping("/mpa")
@Slf4j
@RequiredArgsConstructor
public class MpaController {
    MpaService mpaService;

    /**
     * получение спика всех возрастных ограничений
     * @return список ограничений
     */
    @GetMapping
    public List<Mpa> getAllMpa() {
        return mpaService.listMpa();
    }

    /**
     * получение жанра по идентификтаору
     * @param mpaId
     * @return жанр
     */
    @GetMapping("/{id}")
    public Mpa findMpaById(int mpaId) {
        return mpaService.findMpaById(mpaId);
    }
}

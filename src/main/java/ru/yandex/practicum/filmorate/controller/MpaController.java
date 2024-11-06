package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.List;

@RestController
@RequestMapping("/mpa")
@Slf4j
public class MpaController {
    MpaService mpaService;

    public MpaController(MpaService mpaService) {
        this.mpaService = mpaService;
    }

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
     * @return ограничение
     */
    @GetMapping("/{id}")
    public Mpa findMpaById(Integer mpaId) {
        if (mpaId != null) {
            return mpaService.findMpaById(mpaId);
        } else {
            throw new NotFoundException("Ограничение не передано");
        }
    }
}

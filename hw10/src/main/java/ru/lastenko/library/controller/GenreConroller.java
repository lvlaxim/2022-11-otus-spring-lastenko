package ru.lastenko.library.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.lastenko.library.dto.GenreDto;
import ru.lastenko.library.service.GenreService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GenreConroller {

    private final GenreService genreService;

    @GetMapping("/api/genre")
    public List<GenreDto> getAllGenres() {
        return genreService.getAll();
    }
}

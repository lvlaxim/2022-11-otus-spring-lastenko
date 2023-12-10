package ru.lastenko.library.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.lastenko.library.dto.GenreDto;
import ru.lastenko.library.mapper.GenreMapper;
import ru.lastenko.library.repository.GenreRepository;

@RestController
@RequiredArgsConstructor
public class GenreConroller {

    private final GenreRepository genreRepository;

    private final GenreMapper genreMapper;

    @GetMapping("/api/genre")
    public Flux<GenreDto> getAllGenres() {
        return genreRepository.findAll()
                .map(genreMapper::mapToDto);
    }
}

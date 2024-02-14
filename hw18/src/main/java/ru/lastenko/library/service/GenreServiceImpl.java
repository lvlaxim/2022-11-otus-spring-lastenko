package ru.lastenko.library.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lastenko.library.dto.GenreDto;
import ru.lastenko.library.mapper.GenreMapper;
import ru.lastenko.library.repository.GenreRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    private final GenreMapper genreMapper;

    @Override
    @Transactional(readOnly = true)
    @CircuitBreaker(name = "database", fallbackMethod = "fallbackForGetAll")
    public List<GenreDto> getAll() {
        return genreRepository.findAll()
                .stream()
                .map(genreMapper::mapToDto)
                .toList();
    }

    private List<GenreDto> fallbackForGetAll(Exception e) {
        return List.of();
    }
}
package ru.lastenko.library.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.lastenko.library.dto.AuthorDto;
import ru.lastenko.library.mapper.AuthorMapper;
import ru.lastenko.library.repository.AuthorRepository;


@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorRepository authorRepository;

    private final AuthorMapper authorMapper;

    @GetMapping("/api/author")
    public Flux<AuthorDto> getAllAuthors() {
        return authorRepository.findAll()
                .map(authorMapper::mapToDto);
    }
}

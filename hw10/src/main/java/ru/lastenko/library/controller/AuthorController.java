package ru.lastenko.library.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.lastenko.library.dto.AuthorDto;
import ru.lastenko.library.service.AuthorService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/api/author")
    public List<AuthorDto> getAllAuthors() {
        return authorService.getAll();
    }
}

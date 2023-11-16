package ru.lastenko.library.service;

import ru.lastenko.library.dto.AuthorDto;

import java.util.List;

public interface AuthorService {
    List<AuthorDto> getAll();
}
package ru.lastenko.library.service;

import ru.lastenko.library.domain.Author;

import java.util.List;

public interface AuthorService {
    List<Author> getAll();
}
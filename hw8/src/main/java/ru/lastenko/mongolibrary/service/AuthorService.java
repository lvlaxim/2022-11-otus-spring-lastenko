package ru.lastenko.mongolibrary.service;

import ru.lastenko.mongolibrary.model.Author;

import java.util.List;

public interface AuthorService {
    List<Author> getAll();
}
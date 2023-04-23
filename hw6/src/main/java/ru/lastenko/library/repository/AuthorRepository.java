package ru.lastenko.library.repository;

import ru.lastenko.library.model.Author;

import java.util.List;

public interface AuthorRepository {

    List<Author> getAll();
}
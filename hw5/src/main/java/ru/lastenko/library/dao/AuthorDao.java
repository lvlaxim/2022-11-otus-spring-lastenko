package ru.lastenko.library.dao;

import ru.lastenko.library.domain.Author;

import java.util.List;

public interface AuthorDao {

    List<Author> getAll();
}
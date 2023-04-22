package ru.lastenko.library.dao;

import ru.lastenko.library.domain.Genre;

import java.util.List;

public interface GenreDao {
    List<Genre> getAll();
}

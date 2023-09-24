package ru.lastenko.library.repository;

import ru.lastenko.library.model.Genre;

import java.util.List;

public interface GenreRepository {
    List<Genre> getAll();
}
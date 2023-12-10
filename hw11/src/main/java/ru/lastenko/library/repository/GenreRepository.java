package ru.lastenko.library.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ru.lastenko.library.model.Genre;

public interface GenreRepository extends ReactiveCrudRepository<Genre, String> {
}
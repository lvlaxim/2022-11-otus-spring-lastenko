package ru.lastenko.library.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.lastenko.library.model.Genre;

public interface GenreRepository extends MongoRepository<Genre, String> {
}
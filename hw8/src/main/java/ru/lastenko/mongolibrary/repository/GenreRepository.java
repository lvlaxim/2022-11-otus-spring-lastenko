package ru.lastenko.mongolibrary.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.lastenko.mongolibrary.model.Genre;

public interface GenreRepository extends MongoRepository<Genre, String> {
}
package ru.lastenko.library.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.lastenko.library.model.Author;

public interface AuthorRepository extends MongoRepository<Author, String> {
}
package ru.lastenko.mongolibrary.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.lastenko.mongolibrary.model.Author;

public interface AuthorRepository extends MongoRepository<Author, String> {
}
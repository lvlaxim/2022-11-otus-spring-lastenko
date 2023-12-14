package ru.lastenko.library.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ru.lastenko.library.model.Author;


public interface AuthorRepository extends ReactiveCrudRepository<Author, String> {
}
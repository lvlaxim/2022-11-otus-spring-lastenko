package ru.lastenko.library.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.lastenko.library.model.Book;

public interface BookRepository extends MongoRepository<Book, String> {
}
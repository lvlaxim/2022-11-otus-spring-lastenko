package ru.lastenko.mongolibrary.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.lastenko.mongolibrary.model.Book;

public interface BookRepository extends MongoRepository<Book, String> {
}
package ru.lastenko.library.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ru.lastenko.library.model.Book;

public interface BookRepository extends ReactiveCrudRepository<Book, String> {
}
package ru.lastenko.library.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.lastenko.library.model.Book;

import java.util.List;
import java.util.Optional;

import static ru.lastenko.library.model.Book.Fields.author;
import static ru.lastenko.library.model.Book.Fields.genre;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Override
    @EntityGraph(attributePaths = {author, genre})
    List<Book> findAll();

    @Override
    @EntityGraph(attributePaths = {author, genre})
    Optional<Book> findById(Long id);
}
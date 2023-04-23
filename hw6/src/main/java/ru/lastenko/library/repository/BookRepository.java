package ru.lastenko.library.repository;

import ru.lastenko.library.model.Book;

import java.util.List;

public interface BookRepository {
    List<Book> getAll();

    void insert(Book book);

    Book getById(long id);

    void update(Book book);

    void delete(Book book);

}

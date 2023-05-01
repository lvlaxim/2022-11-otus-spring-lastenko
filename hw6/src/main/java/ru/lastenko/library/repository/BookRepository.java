package ru.lastenko.library.repository;

import ru.lastenko.library.model.Book;

import java.util.List;

public interface BookRepository {

    List<Book> getAll();

    Book insert(Book book);

    Book getBy(long id);

    Book update(Book book);

    void delete(Book book);

}

package ru.lastenko.library.dao;

import ru.lastenko.library.domain.Book;

import java.util.List;

public interface BookDao {
    List<Book> getAll();

    void insert(Book book);

    Book getById(long id);

    void update(Book book);

    void delete(long id);

}

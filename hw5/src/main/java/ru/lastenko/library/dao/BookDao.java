package ru.lastenko.library.dao;

import ru.lastenko.library.domain.Book;

public interface BookDao {
    int insert(Book book);

    Book getById(long id);

    void update(Book book);

    void deleteById(long id);

}

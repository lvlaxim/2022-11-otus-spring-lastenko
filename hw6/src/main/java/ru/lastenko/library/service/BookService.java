package ru.lastenko.library.service;

import ru.lastenko.library.model.Book;

import java.util.List;

public interface BookService {

    List<Book> getAll();

    Book insert(Book book);

    Book getBy(long id);

    Book update(Book book);

    void delete(Book book);
}

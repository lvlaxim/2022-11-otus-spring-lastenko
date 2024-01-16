package ru.lastenko.jpalibrary.service;

import ru.lastenko.jpalibrary.model.Book;

import java.util.List;

public interface BookService {

    List<Book> getAll();

    Book save(Book book);

    Book getBy(long id);

    void delete(Book book);
}
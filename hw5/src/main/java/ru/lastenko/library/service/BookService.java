package ru.lastenko.library.service;

import ru.lastenko.library.domain.Book;

import java.util.List;

public interface BookService {

    List<Book> getAll();

    void getAndSave();

    Book getBy(long id);

    Book update(Book book);

    void delete(Book book);

}

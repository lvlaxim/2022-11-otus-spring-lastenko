package ru.lastenko.mongolibrary.service;

import ru.lastenko.mongolibrary.model.Book;

import java.util.List;

public interface BookService {

    List<Book> getAll();

    Book save(Book book);

    Book getBy(String id);

    void delete(Book book);
}
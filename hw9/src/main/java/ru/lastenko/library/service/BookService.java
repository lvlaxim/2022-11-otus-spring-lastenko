package ru.lastenko.library.service;

import ru.lastenko.library.dto.BookDto;

import java.util.List;

public interface BookService {

    List<BookDto> getAll();

    BookDto save(BookDto book);

    BookDto getBy(long id);

    void deleteBy(long id);
}
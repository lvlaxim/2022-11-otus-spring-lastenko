package ru.lastenko.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lastenko.library.model.Book;
import ru.lastenko.library.repository.BookRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final IOService ioService;

    @Override
    public List<Book> getAll() {
        return bookRepository.getAll();
    }

    @Override
    @Transactional
    public Book insert(Book book) {
        return bookRepository.insert(book);
    }

    @Override
    public Book getBy(long id) {
        Book book = null;
        try {
            book = bookRepository.getBy(id);
        } catch (IllegalArgumentException e) {
            ioService.outputString("Введен несущестующий id!");
        }
        return book;
    }

    @Override
    @Transactional
    public Book update(Book book) {
        return bookRepository.update(book);
    }

    @Override
    @Transactional
    public void delete(Book book) {
        bookRepository.delete(book);
    }


}

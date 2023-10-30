package ru.lastenko.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lastenko.library.model.Book;
import ru.lastenko.library.repository.BookRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final IOService ioService;

    @Override
    @Transactional(readOnly = true)
    public List<Book> getAll() {
        return bookRepository.getAll();
    }

    @Override
    @Transactional
    public Book insert(Book book) {
        return bookRepository.insert(book);
    }

    @Override
    @Transactional(readOnly = true)
    public Book getBy(long id) {
        Book book = null;
        try {
            book = bookRepository.getBy(id);
        } catch (IllegalArgumentException e) {
            ioService.outputString("Книга с введенным id не существует!");
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
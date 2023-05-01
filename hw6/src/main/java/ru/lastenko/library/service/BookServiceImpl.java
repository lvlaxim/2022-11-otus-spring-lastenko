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

    private final BookInputService bookInputService;
    private final BookRepository bookRepository;
    private final IOService ioService;
    private final IdentifiableService identifiableService;

    @Override
    public List<Book> getAll() {
        return bookRepository.getAll();
    }

    @Override
    @Transactional
    public Book getAndSave() {
        Book book = bookInputService.getBook();
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
        Book bookWithUpdates = bookInputService.getBook();
        bookWithUpdates.setId(book.getId());
        return bookRepository.update(bookWithUpdates);
    }

    @Override
    @Transactional
    public void delete(Book book) {
        bookRepository.delete(book);
    }

    @Override
    public Book selectBook() {
        ioService.outputString("Выберите книгу из списка:");
        List<Book> books = getAll();
        return identifiableService.selectByIdFrom(books);
    }
}

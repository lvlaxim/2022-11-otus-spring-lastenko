package ru.lastenko.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lastenko.library.dao.BookDao;
import ru.lastenko.library.domain.Book;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookInputService bookInputService;
    private final BookDao bookDao;
    private final IOService ioService;


    @Override
    public List<Book> getAll() {
        return bookDao.getAll();
    }

    @Override
    public void getAndSave() {
        Book book = bookInputService.getBook();
        bookDao.insert(book);
    }

    @Override
    public Book getBy(long id) {
        Book book = null;
        try {
            book = bookDao.getById(id);
        } catch (IllegalArgumentException e) {
            ioService.outputString("Введен несущестующий id! Выберите из списка:");
        }
        return book;
    }

    @Override
    public Book update(Book book) {
        Book bookWithUpdates = bookInputService.getBook();
        Book updatedBook = new Book(
                book.getId(),
                bookWithUpdates.getName(),
                bookWithUpdates.getAuthor(),
                bookWithUpdates.getGenre());
        bookDao.update(updatedBook);
        return updatedBook;
    }

    @Override
    public void delete(Book book) {
        long bookId = book.getId();
        bookDao.deleteById(bookId);
    }
}

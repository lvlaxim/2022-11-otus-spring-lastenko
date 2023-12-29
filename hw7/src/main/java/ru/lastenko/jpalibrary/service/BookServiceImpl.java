package ru.lastenko.jpalibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lastenko.jpalibrary.repository.BookRepository;
import ru.lastenko.jpalibrary.model.Book;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    @Transactional
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    @Transactional(readOnly = true)
    public Book getBy(long id) {
        return bookRepository.findById(id)
                .orElseThrow();
    }

    @Override
    @Transactional
    public void delete(Book book) {
        bookRepository.delete(book);
    }
}
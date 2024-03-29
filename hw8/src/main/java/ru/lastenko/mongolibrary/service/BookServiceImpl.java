package ru.lastenko.mongolibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lastenko.mongolibrary.model.Book;
import ru.lastenko.mongolibrary.repository.BookRepository;
import ru.lastenko.mongolibrary.repository.CommentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final CommentRepository commentRepository;

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
    public Book getBy(String id) {
        return bookRepository.findById(id)
                .orElseThrow();
    }

    @Override
    @Transactional
    public void delete(Book book) {
        bookRepository.delete(book);
        commentRepository.deleteAllByBook(book);
    }
}
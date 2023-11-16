package ru.lastenko.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lastenko.library.dto.BookDto;
import ru.lastenko.library.mapper.BookMapper;
import ru.lastenko.library.model.Book;
import ru.lastenko.library.repository.BookRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> getAll() {
        return bookRepository.findAll()
                .stream()
                .map(bookMapper::mapToDto)
                .toList();
    }

    @Override
    @Transactional
    public BookDto save(BookDto bookDto) {
        Book book = bookMapper.mapFromDto(bookDto);
        Book savedBook = bookRepository.save(book);
        return bookMapper.mapToDto(savedBook);
    }

    @Override
    @Transactional(readOnly = true)
    public BookDto getBy(long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow();
        return bookMapper.mapToDto(book);
    }

    @Override
    @Transactional
    public void deleteBy(long id) {
        bookRepository.deleteById(id);
    }
}
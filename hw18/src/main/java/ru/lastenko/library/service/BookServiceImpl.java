package ru.lastenko.library.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
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
    @CircuitBreaker(name = "database", fallbackMethod = "fallbackForGetAll")
    public List<BookDto> getAll() {
        return bookRepository.findAll()
                .stream()
                .map(bookMapper::mapToDto)
                .toList();
    }

    @Override
    @Transactional
    @CircuitBreaker(name = "database", fallbackMethod = "fallbackForSave")
    public BookDto save(BookDto bookDto) {
        Book book = bookMapper.mapFromDto(bookDto);
        Book savedBook = bookRepository.save(book);
        return bookMapper.mapToDto(savedBook);
    }

    @Override
    @Transactional(readOnly = true)
    @CircuitBreaker(name = "database", fallbackMethod = "fallbackForGetBy")
    public BookDto getBy(long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow();
        return bookMapper.mapToDto(book);
    }

    @Override
    @Transactional
    @CircuitBreaker(name = "database")
    public void deleteBy(long id) {
        bookRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    @CircuitBreaker(name = "database")
    public boolean isLibraryEmpty() {
        return bookRepository.count() == 0;
    }

    private List<BookDto> fallbackForGetAll(Exception e) {
        return List.of();
    }

    private BookDto fallbackForSave(BookDto bookDto, Exception e) {
        BookDto emptyBookDto = new BookDto();
        emptyBookDto.setId(bookDto.getId());
        return emptyBookDto;
    }

    private BookDto fallbackForGetBy(long id, Exception e) {
        BookDto bookDto = new BookDto();
        bookDto.setId(id);
        return bookDto;
    }

}

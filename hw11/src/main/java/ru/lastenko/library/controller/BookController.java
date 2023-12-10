package ru.lastenko.library.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.lastenko.library.dto.BookDto;
import ru.lastenko.library.mapper.BookMapper;
import ru.lastenko.library.model.Book;
import ru.lastenko.library.repository.BookRepository;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    @GetMapping("/api/book")
    public Flux<BookDto> getAllBooks() {
        return bookRepository.findAll()
                .map(bookMapper::mapToDto);
    }

    @GetMapping("/api/book/{id}")
    public Mono<BookDto> getBook(@PathVariable String id) {
        return bookRepository.findById(id)
                .map(bookMapper::mapToDto);
    }

    @PostMapping("/api/book")
    public Mono<BookDto> addBook(@RequestBody BookDto bookDto) {
        Book book = bookMapper.mapFromDto(bookDto);
        return bookRepository.save(book)
                .map(bookMapper::mapToDto);
    }

    @PutMapping("/api/book")
    public Mono<BookDto> editBook(@RequestBody BookDto bookDto) {
        Book book = bookMapper.mapFromDto(bookDto);
        return bookRepository.save(book)
                .map(bookMapper::mapToDto);
    }

    @DeleteMapping("/api/book/{id}")
    public Mono<Void> deleteBook(@PathVariable String id) {
        return bookRepository.deleteById(id);
    }

}
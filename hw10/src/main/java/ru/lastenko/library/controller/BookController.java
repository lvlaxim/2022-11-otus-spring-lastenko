package ru.lastenko.library.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.lastenko.library.dto.BookDto;
import ru.lastenko.library.service.BookService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/api/book")
    public List<BookDto> getAllBooks() {
        return bookService.getAll();
    }

    @GetMapping("/api/book/{id}")
    public BookDto getBook(@PathVariable long id) {
        return bookService.getBy(id);
    }

    @PostMapping("/api/book")
    public BookDto addBook(@RequestBody BookDto bookDto) {
        return bookService.save(bookDto);
    }

    @PutMapping("/api/book")
    public BookDto editBook(@RequestBody BookDto bookDto) {
        return bookService.save(bookDto);
    }

    @DeleteMapping("/api/book/{id}")
    public void deleteBook(@PathVariable long id) {
        bookService.deleteBy(id);
    }

}
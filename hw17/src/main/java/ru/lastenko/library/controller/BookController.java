package ru.lastenko.library.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.lastenko.library.dto.AuthorDto;
import ru.lastenko.library.dto.BookDto;
import ru.lastenko.library.dto.GenreDto;
import ru.lastenko.library.service.AuthorService;
import ru.lastenko.library.service.BookService;
import ru.lastenko.library.service.GenreService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    @GetMapping({"/", "/book"})
    public String booksList(Model model) {
        List<BookDto> books = bookService.getAll();
        model.addAttribute("books", books);
        return "bookList";
    }

    @GetMapping("/book/new")
    public String addBook(Model model) {
        BookDto book = new BookDto();
        List<AuthorDto> authors = authorService.getAll();
        List<GenreDto> genres = genreService.getAll();
        model.addAttribute("book", book);
        model.addAttribute("genres", genres);
        model.addAttribute("authors", authors);
        return "editBook";
    }

    @GetMapping("/book/{id}")
    public String editBook(@PathVariable("id") long id, Model model) {
        BookDto book = bookService.getBy(id);
        List<AuthorDto> authors = authorService.getAll();
        List<GenreDto> genres = genreService.getAll();
        model.addAttribute("book", book);
        model.addAttribute("genres", genres);
        model.addAttribute("authors", authors);
        return "editBook";
    }

    @PostMapping("/book")
    public String saveBook(@ModelAttribute BookDto bookDto) {
        bookService.save(bookDto);
        return "redirect:/book";
    }

    @PostMapping("/book/delete")
    public String deleteBook(@RequestParam("id") long id) {
        bookService.deleteBy(id);
        return "redirect:/book";
    }

}
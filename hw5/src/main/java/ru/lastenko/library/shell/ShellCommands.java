package ru.lastenko.library.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.lastenko.library.domain.Author;
import ru.lastenko.library.domain.Book;
import ru.lastenko.library.domain.Genre;
import ru.lastenko.library.handler.DbConsoleHandler;
import ru.lastenko.library.service.AuthorService;
import ru.lastenko.library.service.BookService;
import ru.lastenko.library.service.GenreService;
import ru.lastenko.library.service.tostringconvertion.ToStringConversionHandler;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommands {

    private final DbConsoleHandler dbConsoleHandler;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final BookService bookService;
    private final ToStringConversionHandler toStringConversionHandler;

    private Book selectedBook;

    @ShellMethod(value = "Open DB console.", key = {"dbc"})
    public String showDbConsole() {
        return dbConsoleHandler.openConsoleInBrowserAndGetUrlMsg();
    }

    @ShellMethod(value = "Show all authors.", key = {"as"})
    public String showAllAuthors() {
        List<Author> authors = authorService.getAll();
        return toStringConversionHandler.convertToString(authors);
    }

    @ShellMethod(value = "Show all genres.", key = {"gs"})
    public String showAllGenres() {
        List<Genre> genres = genreService.getAll();
        return toStringConversionHandler.convertToString(genres);
    }

    @ShellMethod(value = "Show all books.", key = {"bs"})
    public String showAllBooks() {
        List<Book> books = bookService.getAll();
        return toStringConversionHandler.convertToString(books);
    }

    @ShellMethod(value = "Insert new book.", key = {"i"})
    public String insertBook() {
        bookService.getAndSave();
        return "Книга добавлена!";
    }

    @ShellMethod(value = "Show book by id.", key = {"g"})
    public String showBookById(@ShellOption() long id) {
        Book book = bookService.getBy(id);
        return toStringConversionHandler.convertToString(book);
    }

    @ShellMethod(value = "Update book by id.", key = {"u"})
    public String updateBook() {
        bookService.selectAndUpdate();
        return "Книга обновлена!";
    }

    @ShellMethod(value = "Delete book by id.", key = {"d"})
    public String deleteBookById() {
        bookService.selectAndDelete();
        return "Книга удалена!";
    }

}
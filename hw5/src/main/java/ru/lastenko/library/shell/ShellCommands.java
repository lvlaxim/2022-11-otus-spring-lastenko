package ru.lastenko.library.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.lastenko.library.handler.DbConsoleHandler;
import ru.lastenko.library.service.AuthorService;
import ru.lastenko.library.service.BookService;
import ru.lastenko.library.service.GenreService;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommands {

    private final DbConsoleHandler dbConsoleHandler;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final BookService bookService;

    @ShellMethod(value = "Open DB console.", key = {"dbc"})
    public String showDbConsole() {
        return dbConsoleHandler.openConsoleInBrowserAndGetUrlMsg();
    }

    @ShellMethod(value = "Show all authors.", key = {"as"})
    public String showAllAuthors() {
        return authorService.getAll().toString();
    }

    @ShellMethod(value = "Show all genres.", key = {"gs"})
    public String showAllGenres() {
        return genreService.getAll().toString();
    }

    @ShellMethod(value = "Show all books.", key = {"bs"})
    public String showAllBooks() {
        return bookService.getAll().toString();
    }

    @ShellMethod(value = "Insert new book.", key = {"i"})
    public String insertBook() {
        bookService.getAndSave();
        return "Книга добавлена!";
    }

    @ShellMethod(value = "Show book by id.", key = {"g"})
    public String showBookById(@ShellOption() long id) {
        return bookService.getBy(id).toString();
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
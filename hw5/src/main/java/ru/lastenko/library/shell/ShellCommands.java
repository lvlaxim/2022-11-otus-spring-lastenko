package ru.lastenko.library.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
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

    @ShellMethod(value = "Show all authors.", key = {"a"})
    public String showAllAuthors() {
        return authorService.getAll().toString();
    }

    @ShellMethod(value = "Show all genres.", key = {"g"})
    public String showAllGenres() {
        return genreService.getAll().toString();
    }

    @ShellMethod(value = "Insert new book.", key = {"i"})
    public String insertBook() {
        bookService.inputAndSave();
        return "Книга сохранена!";
    }

}
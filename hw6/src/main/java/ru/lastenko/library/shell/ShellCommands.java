package ru.lastenko.library.shell;

import lombok.RequiredArgsConstructor;
import org.h2.tools.Console;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.lastenko.library.service.CommandProcessor;

import java.sql.SQLException;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommands {

    private final CommandProcessor commandProcessor;

    @ShellMethod(value = "Open DB console.", key = {"dbc"})
    public String openDbConsole() throws SQLException {
        Console.main();
        return "Консоль базы данных открыта.";
    }

    @ShellMethod(value = "Show all authors.", key = {"as"})
    public String showAllAuthors() {
        return commandProcessor.showAllAuthors();
    }

    @ShellMethod(value = "Show all genres.", key = {"gs"})
    public String showAllGenres() {
        return commandProcessor.showAllGenres();
    }

    @ShellMethod(value = "Show all books.", key = {"bs"})
    public String showAllBooks() {
        return commandProcessor.showAllBooks();
    }

    @ShellMethod(value = "Insert new book.", key = {"i"})
    public String insertNewBook() {
        return commandProcessor.insertNewBook();
    }

    @ShellMethod(value = "Show book.", key = {"g"})
    public String showBook() {
        return commandProcessor.getBook();
    }

    @ShellMethod(value = "Update selected book.", key = {"u"})
    public String updateBook() {
        return commandProcessor.updateBook();
    }

    @ShellMethod(value = "Delete selected book.", key = {"d"})
    public String deleteBook() {
        return commandProcessor.deleteBook();
    }

    @ShellMethod(value = "Show all book comments.", key = {"cs"})
    public String showAllCommentsForBook() {
        return commandProcessor.getAllCommentsForBook();
    }

    @ShellMethod(value = "Insert new comment for selected book.", key = {"ic"})
    public String insertNewComment() {
        return commandProcessor.insertNewComment();
    }

    @ShellMethod(value = "Select comment for book.", key = {"gc"})
    public String showCommentForBook() {
        return commandProcessor.getCommentForBook();
    }

    @ShellMethod(value = "Update selected comment.", key = {"uc"})
    public String updateComment() {
        return commandProcessor.updateComment();
    }

    @ShellMethod(value = "Delete selected comment.", key = {"dc"})
    public String deleteComment() {
        return commandProcessor.deleteComment();
    }
}
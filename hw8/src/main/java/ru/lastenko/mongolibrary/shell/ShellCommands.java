package ru.lastenko.mongolibrary.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommands {

    private final ShellCommandProcessor commandProcessor;


    @ShellMethod(value = "Show all authors.", key = {"aa"})
    public String showAllAuthors() {
        return commandProcessor.showAllAuthors();
    }

    @ShellMethod(value = "Show all genres.", key = {"ag"})
    public String showAllGenres() {
        return commandProcessor.showAllGenres();
    }

    @ShellMethod(value = "Show all books.", key = {"ab"})
    public String showAllBooks() {
        return commandProcessor.showAllBooks();
    }

    @ShellMethod(value = "Insert new book.", key = {"ib"})
    public String insertNewBook() {
        return commandProcessor.insertNewBook();
    }

    @ShellMethod(value = "Show book.", key = {"gb"})
    public String showBook() {
        return commandProcessor.getBook();
    }

    @ShellMethod(value = "Update book.", key = {"ub"})
    public String updateBook() {
        return commandProcessor.updateBook();
    }

    @ShellMethod(value = "Delete book.", key = {"db"})
    public String deleteBook() {
        return commandProcessor.deleteBook();
    }

    @ShellMethod(value = "Show all book comments.", key = {"ac"})
    public String showAllCommentsForBook() {
        return commandProcessor.getAllCommentsForBook();
    }

    @ShellMethod(value = "Insert new comment for book.", key = {"ic"})
    public String insertNewComment() {
        return commandProcessor.insertNewComment();
    }

    @ShellMethod(value = "Select comment for book.", key = {"gc"})
    public String showCommentForBook() {
        return commandProcessor.getCommentForBook();
    }

    @ShellMethod(value = "Update comment.", key = {"uc"})
    public String updateComment() {
        return commandProcessor.updateComment();
    }

    @ShellMethod(value = "Delete comment.", key = {"dc"})
    public String deleteComment() {
        return commandProcessor.deleteComment();
    }
}
package ru.lastenko.library.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.lastenko.library.handler.DbConsoleHandler;
import ru.lastenko.library.model.Author;
import ru.lastenko.library.model.Book;
import ru.lastenko.library.model.Comment;
import ru.lastenko.library.model.Genre;
import ru.lastenko.library.service.AuthorService;
import ru.lastenko.library.service.BookService;
import ru.lastenko.library.service.CommentService;
import ru.lastenko.library.service.GenreService;
import ru.lastenko.library.service.tostringconvertion.ToStringConversionHandler;

import java.util.List;

import static java.util.Objects.nonNull;
import static org.springframework.shell.Availability.available;
import static org.springframework.shell.Availability.unavailable;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommands {

    private final DbConsoleHandler dbConsoleHandler;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final BookService bookService;
    private final CommentService commentService;
    private final ToStringConversionHandler toStringConversionHandler;

    private Book selectedBook;
    private Comment selectedComment;

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
        return booksAsString();
    }

    @ShellMethod(value = "Insert new book.", key = {"i"})
    public String insertBook() {
        bookService.getAndSave();
        return booksAsString();
    }

    @ShellMethod(value = "Select book from all.", key = {"g"})
    public String selectBook() {
        selectedBook = bookService.selectBook();
        selectedComment = null;
        return booksAsString();
    }

    @ShellMethod(value = "Select book by id.", key = {"gid"})
    public String selectBookById(@ShellOption() String idAsString) {
        long id;
        try {
            id = Long.parseLong(idAsString);
        } catch (NumberFormatException e) {
            return "Введите число!";
        }
        selectedBook = bookService.getBy(id);
        selectedComment = null;
        return booksAsString();
    }

    @ShellMethod(value = "Update selected book.", key = {"u"})
    @ShellMethodAvailability(value = "bookIsSelected")
    public String updateBook() {
        selectedBook = bookService.update(selectedBook);
        return booksAsString();
    }

    @ShellMethod(value = "Delete selected book.", key = {"d"})
    @ShellMethodAvailability(value = "bookIsSelected")
    public String deleteBookById() {
        bookService.delete(selectedBook);
        selectedBook = null;
        return booksAsString();
    }

    @ShellMethod(value = "Show all book comments.", key = {"cs"})
    @ShellMethodAvailability(value = "bookIsSelected")
    public String getAllBookComments() {
        return bookCommentsAsString();
    }

    @ShellMethod(value = "Insert new comment for selected book.", key = {"ic"})
    @ShellMethodAvailability(value = "bookIsSelected")
    public String insertComment() {
        commentService.insertCommentFor(selectedBook);
        return bookCommentsAsString();
    }

    @ShellMethod(value = "Select comment for book.", key = {"gc"})
    @ShellMethodAvailability(value = "bookIsSelected")
    public String selectBookComment() {
        selectedComment = commentService.selectBookComment(selectedBook);
        return bookCommentsAsString();
    }

    @ShellMethod(value = "Update selected comment.", key = {"uc"})
    @ShellMethodAvailability(value = "commentIsSelected")
    public String updateComment() {
        selectedComment = commentService.update(selectedComment);
        return bookCommentsAsString();
    }

    @ShellMethod(value = "Delete selected comment.", key = {"dc"})
    @ShellMethodAvailability(value = "commentIsSelected")
    public String deleteComment() {
        commentService.delete(selectedComment);
        selectedComment = null;
        return bookCommentsAsString();
    }

    private String booksAsString() {
        List<Book> books = bookService.getAll();
        return toStringConversionHandler.convertToStringWithSelection(books, selectedBook);
    }

    private String bookCommentsAsString() {
        List<Comment> comments = commentService.getAllFor(selectedBook);
        return toStringConversionHandler.convertToStringWithSelection(comments, selectedComment);
    }

    private Availability bookIsSelected() {
        String message = "a book must be selected. Use \"g\" or \"gid\" command to select a book.";
        return nonNull(selectedBook) ? available() : unavailable(message);
    }

    private Availability commentIsSelected() {
        String message = "a comment must be selected. Use \"gc\" or \"gcid\" command to select a comment.";
        return nonNull(selectedComment) ? available() : unavailable(message);
    }
}
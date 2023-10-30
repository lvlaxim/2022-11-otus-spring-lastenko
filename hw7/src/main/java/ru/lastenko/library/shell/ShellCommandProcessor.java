package ru.lastenko.library.shell;

import lombok.RequiredArgsConstructor;
import org.h2.tools.Console;
import org.springframework.stereotype.Component;
import ru.lastenko.library.model.Author;
import ru.lastenko.library.model.Book;
import ru.lastenko.library.model.Comment;
import ru.lastenko.library.model.Genre;
import ru.lastenko.library.service.AuthorService;
import ru.lastenko.library.service.BookInputService;
import ru.lastenko.library.service.BookService;
import ru.lastenko.library.service.CommentInputService;
import ru.lastenko.library.service.CommentService;
import ru.lastenko.library.service.GenreService;
import ru.lastenko.library.service.LibraryUserSelectionService;
import ru.lastenko.library.service.tostringconvertion.ToStringConversionHandler;

import java.sql.SQLException;
import java.util.List;

import static java.lang.System.lineSeparator;

@Component
@RequiredArgsConstructor
public class ShellCommandProcessor {

    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    private final CommentService commentService;

    private final BookInputService bookInputService;

    private final CommentInputService commentInputService;

    private final LibraryUserSelectionService selectionService;

    private final ToStringConversionHandler toStringConversionHandler;

    public String openDbConsole() throws SQLException {
        Console.main();
        return "Консоль базы данных открыта.";
    }

    public String showAllAuthors() {
        List<Author> authors = authorService.getAll();
        return toStringConversionHandler.convertToString(authors);
    }

    public String showAllGenres() {
        List<Genre> genres = genreService.getAll();
        return toStringConversionHandler.convertToString(genres);
    }

    public String showAllBooks() {
        return getAllBooksAsString();
    }

    public String insertNewBook() {
        Book book = bookInputService.getBook();
        bookService.insert(book);
        return getAllBooksAsString();
    }

    public String getBook() {
        Book selectedBook = selectionService.selectBookFromAll();
        long id = selectedBook.getId();
        Book foundBook = bookService.getBy(id);
        return getBookWithAllCommentsAsString(foundBook);
    }

    public String updateBook() {
        Book selectedBook = selectionService.selectBookFromAll();
        Book enteredBook = bookInputService.getBook();
        enteredBook.setId(selectedBook.getId());
        Book updatedBook = bookService.update(enteredBook);
        return toStringConversionHandler.convertToString(updatedBook);
    }

    public String deleteBook() {
        Book selectedBook = selectionService.selectBookFromAll();
        bookService.delete(selectedBook);
        return getAllBooksAsString();
    }

    public String getAllCommentsForBook() {
        Book selectedBook = selectionService.selectBookFromAll();
        return getBookWithAllCommentsAsString(selectedBook);
    }

    public String insertNewComment() {
        Book selectedBook = selectionService.selectBookFromAll();
        Comment comment = commentInputService.getComment();
        comment.setBook(selectedBook);
        Comment insertedComment = commentService.insert(comment);
        return getBookWithAllCommentsAsString(insertedComment.getBook());
    }

    public String getCommentForBook() {
        Comment selectedComment = selectionService.selectBookComment();
        long id = selectedComment.getId();
        Comment foundComment = commentService.getBy(id);
        return toStringConversionHandler.convertToString(foundComment);
    }

    public String updateComment() {
        Comment selectedComment = selectionService.selectBookComment();
        Comment enteredComment = commentInputService.getComment();
        selectedComment.setText(enteredComment.getText());
        Comment updatedComment = commentService.update(selectedComment);
        return getBookWithAllCommentsAsString(updatedComment.getBook());
    }

    public String deleteComment() {
        Comment selectedComment = selectionService.selectBookComment();
        commentService.delete(selectedComment);
        return getBookWithAllCommentsAsString(selectedComment.getBook());
    }

    private String getAllBooksAsString() {
        List<Book> books = bookService.getAll();
        return toStringConversionHandler.convertToString(books);
    }

    private String getBookWithAllCommentsAsString(Book book) {
        List<Comment> comments = commentService.getAllFor(book);
        return toStringConversionHandler.convertToString(book)
                + lineSeparator()
                + toStringConversionHandler.convertToString(comments);
    }
}
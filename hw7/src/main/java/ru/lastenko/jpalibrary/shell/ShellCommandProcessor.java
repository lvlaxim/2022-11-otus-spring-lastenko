package ru.lastenko.jpalibrary.shell;

import lombok.RequiredArgsConstructor;
import org.h2.tools.Console;
import org.springframework.stereotype.Component;
import ru.lastenko.jpalibrary.model.Author;
import ru.lastenko.jpalibrary.model.Book;
import ru.lastenko.jpalibrary.model.Comment;
import ru.lastenko.jpalibrary.model.Genre;
import ru.lastenko.jpalibrary.service.AuthorService;
import ru.lastenko.jpalibrary.service.BookInputService;
import ru.lastenko.jpalibrary.service.BookService;
import ru.lastenko.jpalibrary.service.CommentInputService;
import ru.lastenko.jpalibrary.service.CommentService;
import ru.lastenko.jpalibrary.service.GenreService;
import ru.lastenko.jpalibrary.service.LibraryUserSelectionService;
import ru.lastenko.jpalibrary.service.tostringconvertion.ToStringConversionHandler;

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
        bookService.save(book);
        return getAllBooksAsString();
    }

    public String getBook() {
        Book selectedBook = selectionService.selectBookFromAll();
        long bookId = selectedBook.getId();
        Book foundBook = bookService.getBy(bookId);
        return getBookWithAllCommentsAsString(foundBook);
    }

    public String updateBook() {
        Book selectedBook = selectionService.selectBookFromAll();
        Book enteredBook = bookInputService.getBook();
        long bookId = selectedBook.getId();
        enteredBook.setId(bookId);
        bookService.save(enteredBook);
        Book updatedBook = bookService.getBy(bookId);
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
        Comment insertedComment = commentService.save(comment);
        return getBookWithAllCommentsAsString(insertedComment.getBook());
    }

    public String getCommentForBook() {
        Comment selectedComment = selectionService.selectBookComment();
        long commentId = selectedComment.getId();
        Comment foundComment = commentService.getBy(commentId);
        return toStringConversionHandler.convertToString(foundComment);
    }

    public String updateComment() {
        Comment selectedComment = selectionService.selectBookComment();
        Comment enteredComment = commentInputService.getComment();
        selectedComment.setText(enteredComment.getText());
        Comment updatedComment = commentService.save(selectedComment);
        Book book = bookService.getBy(updatedComment.getBook().getId());
        return getBookWithAllCommentsAsString(book);
    }

    public String deleteComment() {
        Comment selectedComment = selectionService.selectBookComment();
        commentService.delete(selectedComment);
        Book book = bookService.getBy(selectedComment.getBook().getId());
        return getBookWithAllCommentsAsString(book);
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
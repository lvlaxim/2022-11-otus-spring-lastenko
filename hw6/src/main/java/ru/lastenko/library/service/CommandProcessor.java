package ru.lastenko.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.lastenko.library.model.Author;
import ru.lastenko.library.model.Book;
import ru.lastenko.library.model.Comment;
import ru.lastenko.library.model.Genre;
import ru.lastenko.library.service.tostringconvertion.ToStringConversionHandler;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CommandProcessor {

    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    private final CommentService commentService;

    private final BookInputService bookInputService;

    private final CommentInputService commentInputService;

    private final LibraryUserSelectionService selectionService;

    private final ToStringConversionHandler toStringConversionHandler;

    public String showAllAuthors() {
        List<Author> authors = authorService.getAll();
        return toStringConversionHandler.convertToString(authors);
    }

    public String showAllGenres() {
        List<Genre> genres = genreService.getAll();
        return toStringConversionHandler.convertToString(genres);
    }

    public String showAllBooks() {
        return allBooksAsString();
    }

    public String insertNewBook() {
        Book newBook = bookInputService.getBook();
        bookService.insert(newBook);
        return allBooksAsString();
    }

    public String getBook() {
        Book selectedBook = selectionService.selectBookFromAll();
        long id = selectedBook.getId();
        Book foundBook = bookService.getBy(id);
        return toStringConversionHandler.convertToString(foundBook);
    }

    public String updateBook() {
        Book selectedBook = selectionService.selectBookFromAll();
        Book bookWithUpdates = bookInputService.getBook();
        bookWithUpdates.setId(selectedBook.getId());
        bookService.update(bookWithUpdates);
        return allBooksAsString();
    }

    public String deleteBook() {
        Book selectedBook = selectionService.selectBookFromAll();
        bookService.delete(selectedBook);
        return allBooksAsString();
    }

    public String getAllCommentsForBook() {
        Book selectedBook = selectionService.selectBookFromAll();
        List<Comment> comments = commentService.getAllFor(selectedBook);
        return toStringConversionHandler.convertToString(comments);
    }

    public String insertNewComment() {
        Book selectedBook = selectionService.selectBookFromAll();
        Comment comment = commentInputService.getComment();
        comment.setBook(selectedBook);
        commentService.insert(comment);
        return allBooksAsString();
    }

    public String getCommentForBook() {
        Comment selectedComment = selectionService.selectBookComment();
        long id = selectedComment.getId();
        Comment foundComment = commentService.getBy(id);
        return toStringConversionHandler.convertToString(foundComment);
    }

    public String updateComment() {
        Comment selectedComment = selectionService.selectBookComment();
        Comment commentWithUpdates = commentInputService.getComment();
        commentWithUpdates.setId(selectedComment.getId());
        commentWithUpdates.setBook(selectedComment.getBook());
        commentService.update(commentWithUpdates);
        return allBooksAsString();
    }

    public String deleteComment() {
        Comment selectedComment = selectionService.selectBookComment();
        commentService.delete(selectedComment);
        return allBooksAsString();
    }

    private String allBooksAsString() {
        List<Book> books = bookService.getAll();
        return toStringConversionHandler.convertToString(books);
    }
}
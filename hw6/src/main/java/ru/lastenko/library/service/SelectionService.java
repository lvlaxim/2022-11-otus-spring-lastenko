package ru.lastenko.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lastenko.library.model.Author;
import ru.lastenko.library.model.Book;
import ru.lastenko.library.model.Comment;
import ru.lastenko.library.model.Genre;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SelectionService {

    private final IOService ioService;
    private final IdentifiableService identifiableService;
    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final CommentService commentService;

    public Book selectBook() {
        ioService.outputString("Выберите книгу из списка:");
        List<Book> books = bookService.getAll();
        return identifiableService.selectByIdFrom(books);
    }

    public Author selectAuthor() {
        ioService.outputString("Выберите автора книги из списка:");
        List<Author> authors = authorService.getAll();
        return identifiableService.selectByIdFrom(authors);
    }

    public Genre selectGenre() {
        ioService.outputString("Выберите жанр книги из списка:");
        List<Genre> genres = genreService.getAll();
        return identifiableService.selectByIdFrom(genres);
    }

    public Comment selectComment() {
        Book book = selectBook();
        ioService.outputString("Выберите комментарий из списка:");
        List<Comment> comments = commentService.getAllFor(book);
        return identifiableService.selectByIdFrom(comments);
    }
}
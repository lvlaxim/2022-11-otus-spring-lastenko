package ru.lastenko.mongolibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.lastenko.mongolibrary.model.Author;
import ru.lastenko.mongolibrary.model.Book;
import ru.lastenko.mongolibrary.model.Comment;
import ru.lastenko.mongolibrary.model.Genre;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class LibraryUserSelectionService {

    private final IOService ioService;

    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    private final CommentService commentService;

    public Book selectBookFromAll() {
        ioService.outputString("Выберите книгу из списка:");
        List<Book> books = bookService.getAll();
        return letUserSelectById(books, Book::getId);
    }

    public Author selectAuthorFromAll() {
        ioService.outputString("Выберите автора книги из списка:");
        List<Author> authors = authorService.getAll();
        return letUserSelectById(authors, Author::getId);
    }

    public Genre selectGenreFromAll() {
        ioService.outputString("Выберите жанр книги из списка:");
        List<Genre> genres = genreService.getAll();
        return letUserSelectById(genres, Genre::getId);
    }

    public Comment selectBookComment() {
        Book book = selectBookFromAll();
        ioService.outputString("Выберите комментарий из списка:");
        List<Comment> comments = commentService.getAllFor(book);
        return letUserSelectById(comments, Comment::getId);
    }

    private <T> T letUserSelectById(Collection<T> models, Function<T, String> idExtractor) {
        ioService.output(models);
        Optional<T> model = Optional.empty();
        while (model.isEmpty()) {
            ioService.outputString("введите id-номер из списка выше");
            String id = ioService.readString();
            model = models.stream()
                    .filter(m -> id.equals(idExtractor.apply(m)))
                    .findFirst();
        }
        return model.get();
    }
}
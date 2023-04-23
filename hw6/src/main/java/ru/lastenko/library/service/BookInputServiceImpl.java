package ru.lastenko.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lastenko.library.model.Author;
import ru.lastenko.library.model.Book;
import ru.lastenko.library.model.Genre;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookInputServiceImpl implements BookInputService {

    private final IOService ioService;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final IdentifiableService identifiableService;

    public Book getBook() {
        String name = getName();
        Author author = selectAuthor();
        Genre genre = selectGenre();
        return new Book(0, name, author, genre);
    }

    private String getName() {
        ioService.outputString("Введите название книги");
        return ioService.readString();
    }

    private Author selectAuthor() {
        ioService.outputString("Выберите автора книги из списка:");
        List<Author> authors = authorService.getAll();
        return identifiableService.selectByIdFrom(authors);
    }

    private Genre selectGenre() {
        ioService.outputString("Выберите жанр книги из списка:");
        List<Genre> genres = genreService.getAll();
        return identifiableService.selectByIdFrom(genres);
    }
}

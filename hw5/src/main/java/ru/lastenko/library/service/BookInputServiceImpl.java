package ru.lastenko.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lastenko.library.domain.Author;
import ru.lastenko.library.domain.Book;
import ru.lastenko.library.domain.Genre;

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
        Author author = getAuthor();
        Genre genre = getGenre();
        return new Book(0, name, author, genre);
    }

    private String getName() {
        ioService.outputString("Введите название книги");
        return ioService.readString();
    }

    private Author getAuthor() {
        ioService.outputString("Выберите автора книги из списка:");
        List<Author> authors = authorService.getAll();
        return identifiableService.selectByIdFrom(authors);
    }

    private Genre getGenre() {
        ioService.outputString("Выберите жанр книги из списка:");
        List<Genre> genres = genreService.getAll();
        return identifiableService.selectByIdFrom(genres);
    }
}

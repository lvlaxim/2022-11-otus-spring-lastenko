package ru.lastenko.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lastenko.library.model.Author;
import ru.lastenko.library.model.Book;
import ru.lastenko.library.model.Genre;

import static java.util.Collections.emptyList;

@Service
@RequiredArgsConstructor
public class BookInputServiceImpl implements BookInputService {

    private final IOService ioService;

    private final LibraryUserSelectionService libraryUserSelectionService;

    public Book getBook() {
        String name = getName();
        Author author = libraryUserSelectionService.selectAuthorFromAll();
        Genre genre = libraryUserSelectionService.selectGenreFromAll();
        return new Book(0, name, author, genre, emptyList());
    }

    private String getName() {
        ioService.outputString("Введите название книги");
        return ioService.readString();
    }
}
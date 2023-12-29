package ru.lastenko.jpalibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lastenko.jpalibrary.model.Author;
import ru.lastenko.jpalibrary.model.Book;
import ru.lastenko.jpalibrary.model.Genre;

@Service
@RequiredArgsConstructor
public class BookInputServiceImpl implements BookInputService {

    private final IOService ioService;

    private final LibraryUserSelectionService libraryUserSelectionService;

    public Book getBook() {
        String name = getName();
        Author author = libraryUserSelectionService.selectAuthorFromAll();
        Genre genre = libraryUserSelectionService.selectGenreFromAll();
        return new Book(0, name, author, genre);
    }

    private String getName() {
        ioService.outputString("Введите название книги");
        return ioService.readString();
    }
}
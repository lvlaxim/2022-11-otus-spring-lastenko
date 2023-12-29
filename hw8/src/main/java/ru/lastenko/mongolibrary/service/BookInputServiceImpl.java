package ru.lastenko.mongolibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lastenko.mongolibrary.model.Author;
import ru.lastenko.mongolibrary.model.Book;
import ru.lastenko.mongolibrary.model.Genre;

@Service
@RequiredArgsConstructor
public class BookInputServiceImpl implements BookInputService {

    private final IOService ioService;

    private final LibraryUserSelectionService libraryUserSelectionService;

    public Book getBook() {
        String name = getName();
        Author author = libraryUserSelectionService.selectAuthorFromAll();
        Genre genre = libraryUserSelectionService.selectGenreFromAll();
        return new Book(null, name, author, genre);
    }

    private String getName() {
        ioService.outputString("Введите название книги");
        return ioService.readString();
    }
}
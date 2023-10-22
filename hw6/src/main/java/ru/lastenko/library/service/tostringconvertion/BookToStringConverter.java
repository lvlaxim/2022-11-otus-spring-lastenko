package ru.lastenko.library.service.tostringconvertion;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lastenko.library.model.Book;

@Service
@RequiredArgsConstructor
public class BookToStringConverter implements ToStringConverter<Book> {

    private final CommentToStringConverter commentToStringConverter;

    @Override
    public Class<Book> getConvertedClass() {
        return Book.class;
    }

    @Override
    public String convert(Book book) {
        return String.format("Книга - ID: %s,\tназвание: \"%s\",\tавтор: %s,\tжанр: %s",
                book.getId(),
                book.getName(),
                book.getAuthor().getFullName(),
                book.getGenre().getName());
    }
}
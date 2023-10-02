package ru.lastenko.library.service.tostringconvertion;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lastenko.library.model.Book;
import ru.lastenko.library.model.Comment;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.System.lineSeparator;

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
        return String.format("Книга - ID: %s,\tназвание: \"%s\",\tавтор: %s,\tжанр: %s, %s",
                book.getId(),
                book.getName(),
                book.getAuthor().getFullName(),
                book.getGenre().getName(),
                convert(book.getComments()));
    }

    private String convert(List<Comment> comments) {
        if (comments.isEmpty()) {
            return "";
        }
        List<String> commentsAsStrings = comments.stream()
                .map(comment -> "\t\t" + commentToStringConverter.convert(comment))
                .collect(Collectors.toList());
        String commentsAsString = String.join(lineSeparator(), commentsAsStrings);
        return String.format("%s%s", lineSeparator(), commentsAsString);
    }
}
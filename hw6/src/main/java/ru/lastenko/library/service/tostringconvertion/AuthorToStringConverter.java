package ru.lastenko.library.service.tostringconvertion;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lastenko.library.domain.Author;

@Service
@RequiredArgsConstructor
public class AuthorToStringConverter implements ToStringConverter<Author> {

    @Override
    public Class<Author> getConvertedClass() {
        return Author.class;
    }

    @Override
    public String convert(Author author) {
        return String.format("ID: %s, автор: %s", author.getId(), author.getFullName());
    }
}

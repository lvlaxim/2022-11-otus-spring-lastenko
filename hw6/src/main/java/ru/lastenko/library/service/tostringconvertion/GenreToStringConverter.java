package ru.lastenko.library.service.tostringconvertion;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lastenko.library.model.Genre;

@Service
@RequiredArgsConstructor
public class GenreToStringConverter implements ToStringConverter<Genre> {
    @Override
    public Class<Genre> getConvertedClass() {
        return Genre.class;
    }

    @Override
    public String convert(Genre genre) {
        return String.format("ID: %s, жанр: %s", genre.getId(), genre.getName());
    }
}

package ru.lastenko.library.mapper;

import org.mapstruct.Mapper;
import ru.lastenko.library.dto.BookDto;
import ru.lastenko.library.model.Book;


@Mapper(componentModel = "spring",
        uses = {AuthorMapper.class,
                GenreMapper.class})
public interface BookMapper {

    BookDto mapToDto(Book book);

    Book mapFromDto(BookDto bookDto);

}
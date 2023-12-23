package ru.lastenko.library.mapper;

import org.mapstruct.Mapper;
import ru.lastenko.library.dto.AuthorDto;
import ru.lastenko.library.model.Author;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    AuthorDto mapToDto(Author author);

}
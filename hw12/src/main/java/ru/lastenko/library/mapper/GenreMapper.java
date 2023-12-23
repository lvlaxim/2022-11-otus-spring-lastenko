package ru.lastenko.library.mapper;

import org.mapstruct.Mapper;
import ru.lastenko.library.dto.GenreDto;
import ru.lastenko.library.model.Genre;

@Mapper(componentModel = "spring")
public interface GenreMapper {

    GenreDto mapToDto(Genre genre);

}
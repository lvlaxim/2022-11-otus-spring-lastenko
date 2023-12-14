package ru.lastenko.library.dto;

import lombok.Data;

@Data
public class BookDto {

    private String id;

    private String name;

    private AuthorDto author;

    private GenreDto genre;

}
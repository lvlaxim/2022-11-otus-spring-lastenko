package ru.lastenko.library.domain;

import lombok.Data;

@Data
public class Book implements Identifiable {
    private final long id;
    private final String name;
    private final Author author;
    private final Genre genre;
}
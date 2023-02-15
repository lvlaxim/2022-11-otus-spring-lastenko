package ru.lastenko.library.domain;

import lombok.Data;

@Data
public class Author {
    private final long id;
    private final String fullName;
}
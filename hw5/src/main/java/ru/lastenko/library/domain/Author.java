package ru.lastenko.library.domain;

import lombok.Data;

@Data
public class Author implements Identifiable {
    private final long id;
    private final String fullName;
}
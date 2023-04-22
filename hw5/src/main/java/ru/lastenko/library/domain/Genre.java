package ru.lastenko.library.domain;

import lombok.Data;

@Data
public class Genre implements Identifiable {
    private final long id;
    private final String name;
}
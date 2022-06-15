package ru.lastenko.spring.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Answer {
    private final String text;
    private final boolean correct;
}

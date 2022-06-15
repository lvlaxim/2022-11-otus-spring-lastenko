package ru.lastenko.spring.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Question {
    private final String text;
}

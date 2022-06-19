package ru.lastenko.studenttest.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Answer {
    private final String text;
    private final boolean correct;
}

package ru.lastenko.spring.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class Check {
    private final Question question;
    private final List<Answer> answers;

    public boolean hasAnswerOptions() {
        return answers.size() > 1;
    }
}

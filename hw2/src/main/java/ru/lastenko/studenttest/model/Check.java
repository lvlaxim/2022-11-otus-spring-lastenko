package ru.lastenko.studenttest.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
public class Check {
    private final Question question;
    private final List<AnswerOption> answerOptions;

    public boolean hasAnswerOptions() {
        return answerOptions.size() > 1;
    }

    public List<AnswerOption> getRightAnswerOptions() {
        return answerOptions.stream()
                .filter(AnswerOption::isCorrect)
                .collect(Collectors.toList());
    }
}

package ru.lastenko.studenttest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lastenko.studenttest.model.Answer;
import ru.lastenko.studenttest.model.Check;
import ru.lastenko.studenttest.model.Question;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class ConsoleExaminer implements Examiner {

    private final CheckService service;

    @Override
    public void showAllChecks() {
        Collection<Check> checks = service.getAll();
        checks.forEach(this::showCheck);
    }

    private void showCheck(Check check) {
        showQuestion(check.getQuestion());
        if (check.hasAnswerOptions()) {
            showAnswerOptions(check.getAnswers());
        } else {
            System.out.println("There are no options");
        }
        System.out.println();
    }

    private void showQuestion(Question question) {
        System.out.println("Question:");
        System.out.println("\t" + question.getText());
    }

    private void showAnswerOptions(Collection<Answer> answers) {
        System.out.println("Options:");
        for (Answer answer : answers) {
            System.out.println("\t" + answer.getText());
        }
    }
}

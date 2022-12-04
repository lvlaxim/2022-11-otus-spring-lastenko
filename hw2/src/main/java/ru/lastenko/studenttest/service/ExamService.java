package ru.lastenko.studenttest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lastenko.studenttest.model.*;
import ru.lastenko.studenttest.service.modeloutput.ModelOutputService;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExamService {

    private final QuestionService questionService;
    private final ModelOutputService<Question> questionOutputService;
    private final IOService ioService;

    public ExamResult executeExamFor(Student student) {
        Collection<Question> questions = questionService.getAll();
        if (questions.isEmpty()) {
            showExamCancellation();
            return null;
        }
        return interview(student, questions);
    }

    private ExamResult interview(Student student, Collection<Question> questions) {
        int score = 0;
        for (Question question : questions) {
            boolean isAnswerRight = ask(question);
            if (isAnswerRight) {
                score++;
            }
        }
        return new ExamResult(student, score);
    }

    private boolean ask(Question question) {
        questionOutputService.show(question);
        var answer = getAnswer();
        return isStudentRight(answer, question);
    }

    private Answer getAnswer() {
        List<String> answerAsStrings = ioService.readAndSplitStringByCommasWithPrompt("Please enter your answers separated by commas");
        return new Answer(answerAsStrings);
    }

    private boolean isStudentRight(Answer answer, Question question) {
        List<String> rightOptions = question.getRightAnswerOptions().stream()
                .map(AnswerOption::getText)
                .collect(Collectors.toList());
        List<String> answerParts = answer.getParts();
        var isAnswerCorrect = answerParts.containsAll(rightOptions) && rightOptions.containsAll(answerParts);
        return isAnswerCorrect;
    }

    private void showExamCancellation() {
        ioService.outputString("No questions found. The exam has been canceled!");
    }

}

package ru.lastenko.studenttest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lastenko.studenttest.model.*;
import ru.lastenko.studenttest.service.modeloutput.ModelOutputService;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExamService {

    private final QuestionService questionService;
    private final ModelOutputService<Question> questionOutputService;
    private final ExamResultService examResultService;
    private final CommunicationService communicationService;

    public ExamResult executeExamFor(Student student) {
        Collection<Question> questions = questionService.getAll();
        return interviewAndGetResult(student, questions);
    }

    private ExamResult interviewAndGetResult(Student student, Collection<Question> questions) {
        int score = 0;
        for (Question question : questions) {
            boolean answerIsRight = askQuestionThenGetAnswerAndCheckIt(question);
            if (answerIsRight) {
                score++;
            }
        }
        return examResultService.summarizeResult(student, score);
    }

    private boolean askQuestionThenGetAnswerAndCheckIt(Question question) {
        questionOutputService.show(question);
        var answer = getAnswer();
        return checkIfAnswerIsFullAndCorrect(answer, question);
    }

    private Answer getAnswer() {
        Set<String> answerAsStrings = communicationService.showMessageByCodeAndGetFeedbackAsList("prompt.answer");
        return new Answer(answerAsStrings);
    }

    private boolean checkIfAnswerIsFullAndCorrect(Answer answer, Question question) {
        Set<String> rightOptions = question.getRightAnswerOptions().stream()
                .map(AnswerOption::getText)
                .collect(Collectors.toSet());
        Set<String> answerParts = answer.getParts();
        return answerParts.containsAll(rightOptions) && rightOptions.containsAll(answerParts);
    }
}

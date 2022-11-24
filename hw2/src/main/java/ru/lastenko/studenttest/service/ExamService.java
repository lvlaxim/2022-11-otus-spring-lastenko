package ru.lastenko.studenttest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lastenko.studenttest.model.AnswerOption;
import ru.lastenko.studenttest.model.Check;
import ru.lastenko.studenttest.model.ExamResult;
import ru.lastenko.studenttest.model.Student;
import ru.lastenko.studenttest.service.modeloutput.ModelOutputService;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExamService {

    private final CheckService checkService;
    private final ModelOutputService<Check> checkOutputService;
    private final IOService ioService;

    public ExamResult executeExamFor(Student student) {
        Collection<Check> checks = checkService.getAll();
        if (checks.isEmpty()) {
            showExamCancellation();
            return null;
        }
        return interviewStudent(student, checks);
    }

    private ExamResult interviewStudent(Student student, Collection<Check> checks) {
        int score = 0;
        for (Check check : checks) {
            boolean checkPassed = make(check);
            if (checkPassed) {
                score++;
            }
        }
        return new ExamResult(student, score);
    }

    private boolean make(Check check) {
        checkOutputService.show(check);
        List<String> studentAnswers = getStudentAnswer();
        return isStudentAnswerRight(studentAnswers, check);
    }

    private List<String> getStudentAnswer() {
        return ioService.readAndSplitStringByCommasWithPrompt("Please enter your answers separated by commas");
    }

    private boolean isStudentAnswerRight(List<String> studentOptions, Check check) {
        List<String> rightOptions = check.getRightAnswerOptions().stream()
                .map(AnswerOption::getText)
                .collect(Collectors.toList());
        boolean correctOptionsCount = rightOptions.size() == studentOptions.size();
        boolean optionsAreRight = studentOptions.containsAll(rightOptions);
        return correctOptionsCount && optionsAreRight;
    }

    private void showExamCancellation() {
        ioService.outputString("No checks found. The exam has been canceled!");
    }

}

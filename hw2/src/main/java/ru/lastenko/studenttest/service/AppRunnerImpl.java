package ru.lastenko.studenttest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.lastenko.studenttest.model.Check;
import ru.lastenko.studenttest.model.OfferedAnswer;
import ru.lastenko.studenttest.model.Student;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppRunnerImpl implements AppRunner {

    private final CheckService checkService;
    private final StudentService studentService;
    @Value("${threshold:2}")
    private final int threshold;
    private final IOService ioService;

    @Override
    public void executeExam() {
        Student student = studentService.determineCurrentStudent();
        Collection<Check> checks = checkService.getAll();
        if (checks.isEmpty()) {
            showTestCancellation();
            return;
        }
        interviewStudent(student, checks);
        showTestResult(student);
    }

    private void interviewStudent(Student student, Collection<Check> checks) {
        checks.forEach(check -> {
            boolean checkPassed = makeCheck(check);
            if (checkPassed) {
                student.addScore();
            }
        });
    }

    private boolean makeCheck(Check check) {
        checkService.showCheck(check);
        List<String> studentAnswers = getStudentAnswers();
        return areStudentAnswersRight(studentAnswers, check);
    }

    private boolean areStudentAnswersRight(List<String> studentAnswers, Check check) {
        List<String> rightAnswers = check.getRightAnswers().stream()
                .map(OfferedAnswer::getText)
                .collect(Collectors.toList());
        boolean correctAnswersCount = rightAnswers.size() == studentAnswers.size();
        boolean answersRight = studentAnswers.containsAll(rightAnswers);
        return correctAnswersCount && answersRight;
    }

    private void showTestResult(Student student) {
        studentService.showStudentResult(student);
        boolean testPassed = student.getScore() > threshold;
        if (testPassed) {
            showGoodNews();
        } else {
            showBadNews();
        }
    }

    private List<String> getStudentAnswers() {
        return ioService.readAndSplitStringByCommasWithPrompt("Please enter your answers separated by commas");
    }

    private void showGoodNews() {
        ioService.outputString("Congratulations! Test passed.");
    }

    private void showBadNews() {
        ioService.outputString("Sorry, you didn't pass the test.");
    }

    private void showTestCancellation() {
        ioService.outputString("No checks found. Test canceled!");
    }

}

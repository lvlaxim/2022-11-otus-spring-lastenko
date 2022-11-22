package ru.lastenko.studenttest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.lastenko.studenttest.model.ExamResult;

@Service
@RequiredArgsConstructor
public class ExamResultService {

    @Value("${threshold:2}")
    private final int threshold;
    private final IOService ioService;

    public void show(ExamResult examResult) {
        showScore(examResult);
        showOutcome(examResult.getScore());
    }


    private void showScore(ExamResult examResult) {
        var student = examResult.getStudent();
        var result = String.format("%s, your score is %d", student.getName(), examResult.getScore());
        ioService.outputString(result);
    }

    private void showOutcome(int score) {
        boolean testPassed = score > threshold;
        if (testPassed) {
            ioService.outputString("Congratulations! The exam is passed.");
        } else {
            ioService.outputString("Sorry, you didn't pass the exam.");
        }
    }

}

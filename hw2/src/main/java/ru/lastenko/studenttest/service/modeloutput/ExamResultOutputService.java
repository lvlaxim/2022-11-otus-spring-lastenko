package ru.lastenko.studenttest.service.modeloutput;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lastenko.studenttest.model.ExamResult;
import ru.lastenko.studenttest.service.IOService;

@Service
@RequiredArgsConstructor
public class ExamResultOutputService implements ModelOutputService<ExamResult> {

    private final IOService ioService;

    @Override
    public void show(ExamResult examResult) {
        showScore(examResult);
        showOutcome(examResult);
    }


    private void showScore(ExamResult examResult) {
        var student = examResult.getStudent();
        var result = String.format("%s, your score is %d", student.getName(), examResult.getScore());
        ioService.outputString(result);
    }

    private void showOutcome(ExamResult examResult) {
        if (examResult.isPassed()) {
            ioService.outputString("Congratulations! The exam is passed.");
        } else {
            ioService.outputString("Sorry, you didn't pass the exam.");
        }
    }

}

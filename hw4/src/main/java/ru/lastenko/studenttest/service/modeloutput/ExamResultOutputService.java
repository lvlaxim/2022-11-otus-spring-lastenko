package ru.lastenko.studenttest.service.modeloutput;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lastenko.studenttest.model.ExamResult;
import ru.lastenko.studenttest.service.CommunicationService;

@Service
@RequiredArgsConstructor
public class ExamResultOutputService implements ModelOutputService<ExamResult> {

    private final CommunicationService communicationService;

    @Override
    public void show(ExamResult examResult) {
        showScore(examResult);
        showOutcome(examResult);
    }

    private void showScore(ExamResult examResult) {
        var student = examResult.getStudent();
        String name = student.getName();
        int score = examResult.getScore();
        communicationService.showMessageByCode("show.score", name, score);
    }

    private void showOutcome(ExamResult examResult) {
        if (examResult.isPassed()) {
            communicationService.showMessageByCode("congratulation");
        } else {
            communicationService.showMessageByCode("regret");
        }
    }

}

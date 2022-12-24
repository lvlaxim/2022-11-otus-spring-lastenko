package ru.lastenko.studenttest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lastenko.studenttest.config.ApplicationProperties;
import ru.lastenko.studenttest.model.ExamResult;
import ru.lastenko.studenttest.model.Student;

@Service
@RequiredArgsConstructor
public class ExamResultServiceImpl implements ExamResultService {

    private final ApplicationProperties applicationProperties;

    @Override
    public ExamResult summarizeResult(Student student, int score) {
        int threshold = applicationProperties.getThreshold();
        boolean passed = score >= threshold;
        return new ExamResult(student, score, passed);
    }
}

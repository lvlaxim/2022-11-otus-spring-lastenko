package ru.lastenko.studenttest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.lastenko.studenttest.model.ExamResult;
import ru.lastenko.studenttest.model.Student;

@Service
@RequiredArgsConstructor
public class ExamResultServiceImpl implements ExamResultService {

    /*
    @Value стоит здесь, поскольку конструктор генерируется lombok. На самом деле параметр задается через конструктор.
    Можно убедиться в тестах.
     */
    @Value("${threshold:2}")
    private final int threshold;

    @Override
    public ExamResult summarizeResult(Student student, int score) {
        boolean passed = score > threshold;
        return new ExamResult(student, score, passed);
    }
}

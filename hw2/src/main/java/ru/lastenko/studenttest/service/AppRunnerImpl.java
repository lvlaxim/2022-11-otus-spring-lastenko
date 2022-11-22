package ru.lastenko.studenttest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lastenko.studenttest.model.Student;

import static java.util.Objects.nonNull;


@Service
@RequiredArgsConstructor
public class AppRunnerImpl implements AppRunner {

    private final ExamService examService;
    private final StudentService studentService;
    private final ExamResultService examResultService;


    @Override
    public void executeExam() {
        Student student = studentService.determineCurrentStudent();
        var examResult = examService.executeExamFor(student);
        if (nonNull(examResult)) {
            examResultService.show(examResult);
        }
    }

}

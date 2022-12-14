package ru.lastenko.studenttest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lastenko.studenttest.exceptions.QuestionLoadingException;
import ru.lastenko.studenttest.model.ExamResult;
import ru.lastenko.studenttest.model.Student;
import ru.lastenko.studenttest.service.modeloutput.ModelOutputService;


@Service
@RequiredArgsConstructor
public class AppRunnerImpl implements AppRunner {

    private final ExamService examService;
    private final StudentService studentService;
    private final ModelOutputService<ExamResult> examResultOutputService;
    private final IOService ioService;


    @Override
    public void executeExam() {
        try {
            Student student = studentService.determineCurrentStudent();
            var examResult = examService.executeExamFor(student);
            examResultOutputService.show(examResult);
        } catch (QuestionLoadingException e) {
            ioService.outputString("Failed to load questions. The exam has been canceled!");
        }
    }

}

package ru.lastenko.studenttest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;
import ru.lastenko.studenttest.exceptions.QuestionLoadingException;
import ru.lastenko.studenttest.model.ExamResult;
import ru.lastenko.studenttest.model.Student;
import ru.lastenko.studenttest.service.modeloutput.ModelOutputService;

@Service
@RequiredArgsConstructor
public class ApplicationRunnerImpl implements ApplicationRunner {

    private final ExamService examService;
    private final StudentService studentService;
    private final ModelOutputService<ExamResult> examResultOutputService;
    private final CommunicationService communicationService;

    @Override
    public void run(ApplicationArguments args) {
        try {
            Student student = studentService.determineCurrentStudent();
            ExamResult examResult = examService.executeExamFor(student);
            examResultOutputService.show(examResult);
        } catch (QuestionLoadingException e) {
            communicationService.showMessageByCode("failed.load.questions");
        }
    }
}

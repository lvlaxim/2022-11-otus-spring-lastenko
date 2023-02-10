package ru.lastenko.studenttest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lastenko.studenttest.exceptions.QuestionLoadingException;
import ru.lastenko.studenttest.model.ExamResult;
import ru.lastenko.studenttest.model.Student;
import ru.lastenko.studenttest.service.modeloutput.ModelOutputService;

@Service
@RequiredArgsConstructor
public class ExamExecutorImpl implements ExamExecutor {

    private final ExamService examService;
    private final StudentService studentService;
    private final ModelOutputService<ExamResult> examResultOutputService;
    private final CommunicationService communicationService;
    @Override
    public void executeExam() {
        try {
            Student student = studentService.determineCurrentStudent();
            ExamResult examResult = examService.executeExamFor(student);
            examResultOutputService.show(examResult);
        } catch (QuestionLoadingException e) {
            communicationService.showMessageByCode("failed.load.questions");
        }
    }
}

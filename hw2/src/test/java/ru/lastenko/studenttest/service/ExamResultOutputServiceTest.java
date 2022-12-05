package ru.lastenko.studenttest.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.lastenko.studenttest.model.ExamResult;
import ru.lastenko.studenttest.model.Student;
import ru.lastenko.studenttest.service.modeloutput.ExamResultOutputService;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("Сервис обрабатывающий результат экзамена")
@ExtendWith(MockitoExtension.class)
class ExamResultOutputServiceTest {

    private static final Student STUDENT = new Student("Ivan", "Ivanov");

    @Mock
    private IOService ioService;

    @Test
    @DisplayName("должен вывести набранные баллы, поздравить со здачей экзамена")
    void shouldCongratulateOnPassingExam() {
        var examResultOutputService = new ExamResultOutputService(ioService);
        var examResult = new ExamResult(STUDENT, 6, true);

        examResultOutputService.show(examResult);

        verify(ioService, times(1)).outputString("Ivan, your score is 6");
        verify(ioService, times(1)).outputString("Congratulations! The exam is passed.");
    }

    @Test
    @DisplayName("должен вывести набранные баллы, огорчить провалом экзамена")
    void shouldRegretExamFailed() {
        var examResultOutputService = new ExamResultOutputService(ioService);
        var examResult = new ExamResult(STUDENT, 4, false);

        examResultOutputService.show(examResult);

        verify(ioService, times(1)).outputString("Ivan, your score is 4");
        verify(ioService, times(1)).outputString("Sorry, you didn't pass the exam.");
    }

}
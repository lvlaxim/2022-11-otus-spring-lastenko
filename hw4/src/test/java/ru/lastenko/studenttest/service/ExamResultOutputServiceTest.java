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
    private CommunicationService communicationService;

    @Test
    @DisplayName("должен вывести набранные баллы, поздравить со здачей экзамена")
    void shouldCongratulateOnPassingExam() {
        var examResultOutputService = new ExamResultOutputService(communicationService);
        var examResult = new ExamResult(STUDENT, 6, true);

        examResultOutputService.show(examResult);

        verify(communicationService, times(1))
                .showMessageByCode("show.score", "Ivan", 6);
        verify(communicationService, times(1)).showMessageByCode("congratulation");
    }

    @Test
    @DisplayName("должен вывести набранные баллы, огорчить провалом экзамена")
    void shouldRegretExamFailed() {
        var examResultOutputService = new ExamResultOutputService(communicationService);
        var examResult = new ExamResult(STUDENT, 4, false);

        examResultOutputService.show(examResult);

        verify(communicationService, times(1))
                .showMessageByCode("show.score", "Ivan", 4);
        verify(communicationService, times(1)).showMessageByCode("regret");
    }

}
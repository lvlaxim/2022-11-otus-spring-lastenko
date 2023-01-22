package ru.lastenko.studenttest.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.lastenko.studenttest.config.ApplicationProperties;
import ru.lastenko.studenttest.model.ExamResult;
import ru.lastenko.studenttest.model.Student;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Сервис для работы с результатом тестирования")
class ExamResultServiceImplTest {

    private static final ApplicationProperties APPLICATION_PROPERTIES = new ApplicationProperties();
    private static final Student STUDENT = new Student("Ivan", "Ivanov");

    @Test
    @DisplayName("должен создать результат для студента, прошедшего тест")
    void studentShouldPassExam() {
        int score = 4;
        var examResultService = new ExamResultServiceImpl(APPLICATION_PROPERTIES);

        ExamResult actualExamResult = examResultService.summarizeResult(STUDENT, score);

        var expectedExamResult = new ExamResult(STUDENT, score, true);
        assertThat(expectedExamResult)
                .usingRecursiveComparison()
                .isEqualTo(actualExamResult);
    }

    @Test
    @DisplayName("должен создать результат для студента, провалившего тест")
    void studentShouldNotPassExam() {
        int score = 2;
        var examResultService = new ExamResultServiceImpl(APPLICATION_PROPERTIES);

        ExamResult actualExamResult = examResultService.summarizeResult(STUDENT, score);

        var expectedExamResult = new ExamResult(STUDENT, score, false);
        assertThat(expectedExamResult)
                .usingRecursiveComparison()
                .isEqualTo(actualExamResult);
    }

}
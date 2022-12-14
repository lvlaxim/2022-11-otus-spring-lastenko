package ru.lastenko.studenttest.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.lastenko.studenttest.model.ExamResult;
import ru.lastenko.studenttest.model.Student;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Сервис для работы с результатом тестирования")
class ExamResultServiceImplTest {

    private static final Student STUDENT = new Student("Ivan", "Ivanov");
    private static final int THRESHOLD = 3;

    @Test
    @DisplayName("должен создать результат для студента, прошедшего тест")
    void studentShouldPassExam() {
        int score = 4;
        var examResultService = new ExamResultServiceImpl(THRESHOLD);

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
        var examResultService = new ExamResultServiceImpl(THRESHOLD);

        ExamResult actualExamResult = examResultService.summarizeResult(STUDENT, score);

        var expectedExamResult = new ExamResult(STUDENT, score, false);
        assertThat(expectedExamResult)
                .usingRecursiveComparison()
                .isEqualTo(actualExamResult);
    }

}
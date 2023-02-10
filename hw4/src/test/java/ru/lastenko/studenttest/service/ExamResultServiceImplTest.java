package ru.lastenko.studenttest.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.lastenko.studenttest.dao.QuestionDao;
import ru.lastenko.studenttest.i18n.LocaleToggle;
import ru.lastenko.studenttest.model.ExamResult;
import ru.lastenko.studenttest.model.Student;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Сервис для работы с результатом тестирования")
@SpringBootTest
@ExtendWith(SpringExtension.class)
class ExamResultServiceImplTest {
    private static final Student STUDENT = new Student("Ivan", "Ivanov");

    @MockBean
    private IOService ioService;
    @MockBean
    private QuestionDao questionDao;
    @MockBean
    private LocaleToggle localeToggle;
    @Autowired
    private ExamResultService examResultService;

    @Test
    @DisplayName("должен создать результат для студента, прошедшего тест")
    void studentShouldPassExam() {
        int score = 4;

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

        ExamResult actualExamResult = examResultService.summarizeResult(STUDENT, score);

        var expectedExamResult = new ExamResult(STUDENT, score, false);
        assertThat(expectedExamResult)
                .usingRecursiveComparison()
                .isEqualTo(actualExamResult);
    }

}
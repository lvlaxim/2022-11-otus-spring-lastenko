package ru.lastenko.studenttest.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.lastenko.studenttest.model.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@DisplayName("Сервис проведения экзамена")
@ExtendWith(MockitoExtension.class)
class ExamServiceTest {

    private static final Student STUDENT = new Student("Ivan", "Ivanov");
    private static final String QUESTION = "question";
    private static final String RIGHT_ANSWER = "rightAnswer";
    private static final String WRONG_ANSWER = "wrongAnswer";


    private ExamService examService;
    @Mock
    private CheckService checkService;
    @Mock
    private IOService ioService;
    List<Check> checks;

    @BeforeEach
    void setUp() {
        examService = new ExamService(checkService, ioService);
        checks = List.of(getCheck(), getCheck(), getCheck());
        when(checkService.getAll()).thenReturn(checks);
    }

    @Test
    @DisplayName("должен провести эказамен для студента-отличника (отвечает правильно)")
    void shouldExecuteExamForGoodStudent() {
        when(ioService.readAndSplitStringByCommasWithPrompt("Please enter your answers separated by commas"))
                .thenReturn(List.of(RIGHT_ANSWER));

        var actualExamResult = examService.executeExamFor(STUDENT);

        verify(checkService, times(1)).getAll();
        verify(checkService, times(3)).showCheck(any(Check.class));
        verify(ioService, times(3))
                .readAndSplitStringByCommasWithPrompt("Please enter your answers separated by commas");
        var expectedExamResult = new ExamResult(STUDENT, 3);
        assertThat(actualExamResult)
                .usingRecursiveComparison()
                .isEqualTo(expectedExamResult);

    }

    @Test
    @DisplayName("должен провести эказамен для студента-двоичника (отвечает неправильно)")
    void shouldExecuteExamForBadStudent() {
        when(ioService.readAndSplitStringByCommasWithPrompt("Please enter your answers separated by commas"))
                .thenReturn(List.of(WRONG_ANSWER));

        var actualExamResult = examService.executeExamFor(STUDENT);

        verify(checkService, times(1)).getAll();
        verify(checkService, times(3)).showCheck(any(Check.class));
        verify(ioService, times(3))
                .readAndSplitStringByCommasWithPrompt("Please enter your answers separated by commas");
        var expectedExamResult = new ExamResult(STUDENT, 0);
        assertThat(actualExamResult)
                .usingRecursiveComparison()
                .isEqualTo(expectedExamResult);

    }

    private Check getCheck() {
        var question = new Question(QUESTION);
        List<AnswerOption> answerOptions = List.of(
                new AnswerOption(RIGHT_ANSWER, true),
                new AnswerOption(WRONG_ANSWER, false));
        return new Check(question, answerOptions);
    }
}
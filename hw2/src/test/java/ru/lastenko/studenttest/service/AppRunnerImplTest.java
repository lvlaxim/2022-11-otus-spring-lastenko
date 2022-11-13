package ru.lastenko.studenttest.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.lastenko.studenttest.model.Check;
import ru.lastenko.studenttest.model.OfferedAnswer;
import ru.lastenko.studenttest.model.Question;
import ru.lastenko.studenttest.model.Student;

import java.util.List;

import static org.mockito.Mockito.*;

@DisplayName("Приложение должено ")
@ExtendWith(MockitoExtension.class)
class AppRunnerImplTest {

    private static final String QUESTION = "question";
    private static final String RIGHT_ANSWER = "rightAnswer";
    private static final String WRONG_ANSWER = "wrongAnswer";
    private static final int THRESHOLD = 3;

    private AppRunner appRunner;
    @Mock
    private CheckService checkService;
    @Mock
    private StudentService studentService;
    @Mock
    private IOService ioService;
    private List<Check> checks;

    @BeforeEach
    void setUp() {
        appRunner = new AppRunnerImpl(checkService, studentService, THRESHOLD, ioService);

        Check check = getCheck();
        checks = List.of(check);
        when(checkService.getAll()).thenReturn(checks);
    }

    @Test
    @DisplayName("провести тест для студента-отличника (отвечает правильно)")
    void shouldMakeTestWithRightStudentAnswer() {
        var student = mock(Student.class);
        when(studentService.determineCurrentStudent()).thenReturn(student);
        when(ioService.readAndSplitStringByCommasWithPrompt("Please enter your answers separated by commas"))
                .thenReturn(List.of(RIGHT_ANSWER));
        when(student.getScore()).thenReturn(THRESHOLD + 1);

        appRunner.executeExam();

        verify(studentService, times(1)).determineCurrentStudent();
        verify(checkService, times(1)).getAll();
        checks.forEach(check -> {
            verify(checkService, times(1)).showCheck(check);
            verify(ioService, times(1))
                    .readAndSplitStringByCommasWithPrompt("Please enter your answers separated by commas");
        });
        verify(student, atLeastOnce()).addScore();
        verify(studentService, times(1)).showStudentResult(student);
        verify(ioService, times(1)).outputString("Congratulations! Test passed.");
    }

    @Test
    @DisplayName("провести тест для студента-двоичника (отвечает неправильно)")
    void shouldMakeTestWithWrongStudentAnswer() {
        var student = mock(Student.class);
        when(studentService.determineCurrentStudent()).thenReturn(student);
        when(ioService.readAndSplitStringByCommasWithPrompt("Please enter your answers separated by commas"))
                .thenReturn(List.of(WRONG_ANSWER));
        when(student.getScore()).thenReturn(THRESHOLD);

        appRunner.executeExam();

        verify(studentService, times(1)).determineCurrentStudent();
        verify(checkService, times(1)).getAll();
        checks.forEach(check -> {
            verify(checkService, times(1)).showCheck(check);
            verify(ioService, times(1))
                    .readAndSplitStringByCommasWithPrompt("Please enter your answers separated by commas");
        });
        verify(student, never()).addScore();
        verify(studentService, times(1)).showStudentResult(student);
        verify(ioService, times(1)).outputString("Sorry, you didn't pass the test.");
    }

    private Check getCheck() {
        var question = new Question(QUESTION);
        List<OfferedAnswer> offeredAnswers = List.of(
                new OfferedAnswer(RIGHT_ANSWER, true),
                new OfferedAnswer(WRONG_ANSWER, false));
        return new Check(question, offeredAnswers);
    }
}
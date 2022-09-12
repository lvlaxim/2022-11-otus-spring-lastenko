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

@DisplayName("Экзаменатор должен ")
@ExtendWith(MockitoExtension.class)
class ExaminerImplTest {

    public static final String QUESTION = "question";
    public static final String RIGHT_ANSWER = "rightAnswer";
    public static final String WRONG_ANSWER = "wrongAnswer";
    public static final int THRESHOLD = 3;

    Examiner examiner;
    @Mock
    CheckService checkService;
    @Mock
    StudentService studentService;
    List<Check> checks;

    @BeforeEach
    void setUp() {
        examiner = new ExaminerImpl(checkService, studentService, THRESHOLD);

        Check check = getCheck();
        checks = List.of(check);
        when(checkService.getAll()).thenReturn(checks);
    }

    @Test
    @DisplayName("показать все проверки")
    void shouldShowAllChecks() {
        examiner.showAllChecks();

        verify(checkService, times(1)).getAll();
        verify(checkService, times(1)).showChecks(checks);
    }

    @Test
    @DisplayName("провести тест для студента-отличника (отвечает правильно)")
    void shouldMakeTestWithRightStudentAnswer() {
        var student = mock(Student.class);
        when(studentService.getStudent()).thenReturn(student);
        when(studentService.getStudentAnswers()).thenReturn(List.of(RIGHT_ANSWER));
        when(student.getScore()).thenReturn(THRESHOLD + 1);

        examiner.makeTest();

        verify(studentService, times(1)).getStudent();
        verify(checkService, times(1)).getAll();
        checks.forEach(check -> {
            verify(checkService, times(1)).showCheck(check);
            verify(studentService, times(1)).getStudentAnswers();
        });
        verify(student, atLeastOnce()).addScore();
        verify(studentService, times(1)).showStudentResult(student);
    }

    @Test
    @DisplayName("провести тест для студента-двоичника (отвечает неправильно)")
    void shouldMakeTestWithWrongStudentAnswer() {
        var student = mock(Student.class);
        when(studentService.getStudent()).thenReturn(student);
        when(studentService.getStudentAnswers()).thenReturn(List.of(WRONG_ANSWER));
        when(student.getScore()).thenReturn(THRESHOLD);

        examiner.makeTest();

        verify(studentService, times(1)).getStudent();
        verify(checkService, times(1)).getAll();
        checks.forEach(check -> {
            verify(checkService, times(1)).showCheck(check);
            verify(studentService, times(1)).getStudentAnswers();
        });
        verify(student, never()).addScore();
        verify(studentService, times(1)).showStudentResult(student);
    }

    private Check getCheck() {
        var question = new Question(QUESTION);
        List<OfferedAnswer> offeredAnswers = List.of(
                new OfferedAnswer(RIGHT_ANSWER, true),
                new OfferedAnswer(WRONG_ANSWER, false));
        return new Check(question, offeredAnswers);
    }
}
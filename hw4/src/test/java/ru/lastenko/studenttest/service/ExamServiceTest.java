package ru.lastenko.studenttest.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.lastenko.studenttest.model.AnswerOption;
import ru.lastenko.studenttest.model.Question;
import ru.lastenko.studenttest.model.Student;
import ru.lastenko.studenttest.service.modeloutput.QuestionOutputService;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    private QuestionService questionService;
    @Mock
    private QuestionOutputService questionOutputService;
    @Mock
    private ExamResultService examResultService;
    @Mock
    private CommunicationService communicationService;

    @BeforeEach
    void setUp() {
        examService = new ExamService(questionService, questionOutputService, examResultService, communicationService);
        List<Question> questions = List.of(getQuestion(), getQuestion(), getQuestion());
        when(questionService.getAll()).thenReturn(questions);
    }

    @Test
    @DisplayName("должен провести эказамен для студента-отличника (отвечает правильно)")
    void shouldExecuteExamForGoodStudent() {
        when(communicationService.showMessageByCodeAndGetFeedbackAsList("prompt.answer"))
                .thenReturn(Set.of(RIGHT_ANSWER));

        examService.executeExamFor(STUDENT);

        verify(questionService, times(1)).getAll();
        verify(questionOutputService, times(3)).show(any(Question.class));
        verify(communicationService, times(3))
                .showMessageByCodeAndGetFeedbackAsList("prompt.answer");
        var scoreCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(examResultService, times(1)).summarizeResult(eq(STUDENT), scoreCaptor.capture());
        Integer actualScore = scoreCaptor.getValue();
        assertEquals(3, actualScore);
    }

    @Test
    @DisplayName("должен провести эказамен для студента-двоичника (отвечает неправильно)")
    void shouldExecuteExamForBadStudent() {
        when(communicationService.showMessageByCodeAndGetFeedbackAsList("prompt.answer"))
                .thenReturn(Set.of(WRONG_ANSWER));

        examService.executeExamFor(STUDENT);

        verify(questionService, times(1)).getAll();
        verify(questionOutputService, times(3)).show(any(Question.class));
        verify(communicationService, times(3))
                .showMessageByCodeAndGetFeedbackAsList("prompt.answer");
        var scoreCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(examResultService, times(1)).summarizeResult(eq(STUDENT), scoreCaptor.capture());
        Integer actualScore = scoreCaptor.getValue();
        assertEquals(0, actualScore);

    }

    private Question getQuestion() {
        List<AnswerOption> answerOptions = List.of(
                new AnswerOption(RIGHT_ANSWER, true),
                new AnswerOption(WRONG_ANSWER, false));
        return new Question(QUESTION, answerOptions);
    }
}
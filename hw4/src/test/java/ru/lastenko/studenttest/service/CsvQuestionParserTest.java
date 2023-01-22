package ru.lastenko.studenttest.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.lastenko.studenttest.model.AnswerOption;
import ru.lastenko.studenttest.model.Question;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("Парсер объектов класса Question из строк")
class CsvQuestionParserTest {

    @Test
    @DisplayName("должен распарсить объект класса Question из строки")
    void parseStringToQuestion() {
        var parser = new CsvQuestionParser();
        String questionAsCsvString = "locale;question;rightAnswer;TRUE;wrongAnswer1;FALSE;wrongAnswer2;FALSE";
        List<AnswerOption> answerOptions = List.of(
                new AnswerOption("rightAnswer", true),
                new AnswerOption("wrongAnswer1", false),
                new AnswerOption("wrongAnswer2", false));
        Question expectedQuestion = new Question("question", answerOptions);

        Question actualQuestion = parser.parseQuestionFrom(questionAsCsvString);

        assertThat(actualQuestion).usingRecursiveComparison().isEqualTo(expectedQuestion);
    }

    @Test
    @DisplayName("должен вызвать метод parseQuestionFrom для каждой строки и вернуть массив соответствующего размера")
    void parseStringsToQuestions() {
        var parserMock = Mockito.mock(CsvQuestionParser.class);
        when(parserMock.parseQuestionsFrom(any())).thenCallRealMethod();
        List<String> questionsAsStrings = List.of("string1", "string2", "string3");

        List<Question> questions = parserMock.parseQuestionsFrom(questionsAsStrings);

        questionsAsStrings.forEach(questionAsString ->
                verify(parserMock, times(1)).parseQuestionFrom(questionAsString));
        assertThat(questions).asList()
                .isNotNull()
                .hasSize(questionsAsStrings.size());
    }
}
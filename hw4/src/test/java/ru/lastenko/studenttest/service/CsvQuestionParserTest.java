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
import ru.lastenko.studenttest.model.AnswerOption;
import ru.lastenko.studenttest.model.Question;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("Парсер объектов класса Question из строк")
@SpringBootTest
@ExtendWith(SpringExtension.class)
class CsvQuestionParserTest {

    @MockBean
    private IOService ioService;
    @MockBean
    private QuestionDao questionDao;
    @MockBean
    private LocaleToggle localeToggle;
    @Autowired
    private CsvQuestionParser parser;

    @Test
    @DisplayName("должен распарсить объект класса Question из строки")
    void parseStringToQuestion() {
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
        List<String> questionsAsStrings = List.of(
                "locale;question;rightAnswer;TRUE;wrongAnswer1;FALSE;wrongAnswer2;FALSE",
                "locale;question;rightAnswer;TRUE;wrongAnswer1;FALSE;wrongAnswer2;FALSE",
                "locale;question;rightAnswer;TRUE;wrongAnswer1;FALSE;wrongAnswer2;FALSE");

        List<Question> questions = parser.parseQuestionsFrom(questionsAsStrings);

        assertThat(questions).asList()
                .isNotNull()
                .hasSize(questionsAsStrings.size());
    }
}
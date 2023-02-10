package ru.lastenko.studenttest.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.lastenko.studenttest.config.ApplicationProperties;
import ru.lastenko.studenttest.exceptions.QuestionLoadingException;
import ru.lastenko.studenttest.service.QuestionParser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@DisplayName("DAO класс для работы с *.csv файлами")
@SpringBootTest(classes = {CsvQuestionDao.class})
@ExtendWith(SpringExtension.class)
class CsvQuestionDaoTest {

    @MockBean
    private Resource resource;
    @MockBean
    private ResourceLoader resourceLoader;
    @MockBean
    private QuestionParser<String> parser;
    @MockBean
    private ApplicationProperties applicationProperties;
    @Autowired
    private CsvQuestionDao csvQuestionDao;

    @Test
    @DisplayName("должен получить записи с проверками из ресурсов и распарсить их")
    void shouldGetAll() throws IOException {
        when(applicationProperties.getFile()).thenReturn("file");
        when(resourceLoader.getResource(any(String.class))).thenReturn(resource);
        String questionAsCsvStringWithLocale = "en;question;rightAnswer;TRUE;wrongAnswer1;FALSE;wrongAnswer2;FALSE";
        InputStream inputStream = new ByteArrayInputStream(questionAsCsvStringWithLocale.getBytes(StandardCharsets.UTF_8));
        when(resource.getInputStream()).thenReturn(inputStream);
        Locale locale = new Locale("en");
        when(applicationProperties.getLocale()).thenReturn(locale);

        csvQuestionDao.getAll();

        verify(resource, times(1)).getInputStream();
        ArgumentCaptor<List<String>> argumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(parser, times(1)).parseQuestionsFrom(argumentCaptor.capture());
        List<String> questionsAsCsvStrings = argumentCaptor.getValue();
        String questionAsCsvString = "en;question;rightAnswer;TRUE;wrongAnswer1;FALSE;wrongAnswer2;FALSE";
        assertEquals(questionAsCsvString, questionsAsCsvStrings.get(0));
    }

    @Test
    @DisplayName("должен выкинуть ошибку, поскольку источник отсутствует")
    void shouldThrowExceptionCauseSourceIsEmpty() throws IOException {
        var inputStream = mock(InputStream.class);
        when(resource.getInputStream()).thenReturn(inputStream);

        var exception = assertThrows(QuestionLoadingException.class, csvQuestionDao::getAll);

        String expectedMessage = "Failed to load questions from csv file.";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("должен выкинуть ошибку, поскольку источник пуст")
    void shouldThrowExceptionCauseDataIsNotCorrect() throws IOException {
        String questionAsCsvString = "";
        InputStream inputStream = new ByteArrayInputStream(questionAsCsvString.getBytes(StandardCharsets.UTF_8));
        when(resource.getInputStream()).thenReturn(inputStream);

        var exception = assertThrows(QuestionLoadingException.class, csvQuestionDao::getAll);

        String expectedMessage = "The file is empty.";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }
}
package ru.lastenko.studenttest.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.core.io.Resource;
import ru.lastenko.studenttest.exceptions.QuestionLoadingException;
import ru.lastenko.studenttest.i18n.LocaleHolder;
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
class CsvQuestionDaoTest {

    @Test
    @DisplayName("должен получить записи с проверками из ресурсов и распарсить их")
    void shouldGetAll() throws IOException {
        String questionAsCsvStringWithLocale = "en;question;rightAnswer;TRUE;wrongAnswer1;FALSE;wrongAnswer2;FALSE";
        InputStream inputStream = new ByteArrayInputStream(questionAsCsvStringWithLocale.getBytes(StandardCharsets.UTF_8));
        var resource = mock(Resource.class);
        when(resource.getInputStream()).thenReturn(inputStream);
        var parser = mock(QuestionParser.class);
        var localeProvider = mock(LocaleHolder.class);
        Locale locale = new Locale("en");
        when(localeProvider.getLocale()).thenReturn(locale);
        CsvQuestionDao csvQuestionDao = new CsvQuestionDao(resource, parser, localeProvider);

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
        var resource = mock(Resource.class);
        var inputStream = mock(InputStream.class);
        when(resource.getInputStream()).thenReturn(inputStream);
        var parser = mock(QuestionParser.class);
        var localeProvider = mock(LocaleHolder.class);
        CsvQuestionDao csvQuestionDao = new CsvQuestionDao(resource, parser, localeProvider);

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
        var resource = mock(Resource.class);
        when(resource.getInputStream()).thenReturn(inputStream);
        var parser = mock(QuestionParser.class);
        var localeProvider = mock(LocaleHolder.class);
        CsvQuestionDao csvQuestionDao = new CsvQuestionDao(resource, parser, localeProvider);

        var exception = assertThrows(QuestionLoadingException.class, csvQuestionDao::getAll);

        String expectedMessage = "The file is empty.";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }
}
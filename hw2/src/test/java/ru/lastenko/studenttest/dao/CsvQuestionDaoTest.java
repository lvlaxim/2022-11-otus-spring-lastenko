package ru.lastenko.studenttest.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.core.io.Resource;
import ru.lastenko.studenttest.exceptions.QuestionLoadingException;
import ru.lastenko.studenttest.service.QustionParser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@DisplayName("DAO класс для работы с *.csv файлами")
class CsvQuestionDaoTest {

    @Test
    @DisplayName("должен получить записи с проверками из ресурсов и распарсить их")
    void shouldGetAll() throws IOException {
        String questionAsCsvString = "question;rightAnswer;TRUE;wrongAnswer1;FALSE;wrongAnswer2;FALSE";
        InputStream inputStream = new ByteArrayInputStream(questionAsCsvString.getBytes(StandardCharsets.UTF_8));
        var resource = mock(Resource.class);
        when(resource.getInputStream()).thenReturn(inputStream);
        var parser = mock(QustionParser.class);
        CsvQuestionDao csvQuestionDao = new CsvQuestionDao(resource, parser);

        csvQuestionDao.getAll();

        verify(resource, times(1)).getInputStream();
        ArgumentCaptor<List<String>> argumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(parser, times(1)).parseQuestionsFrom(argumentCaptor.capture());
        List<String> questionsAsCsvStrings = argumentCaptor.getValue();
        assertEquals(questionAsCsvString, questionsAsCsvStrings.get(0));
    }

    @Test
    @DisplayName("должен выкинуть ошибку, поскольку источник отсутствует")
    void shouldThrowExceptionCauseSourceIsEmpty() throws IOException {
        var resource = mock(Resource.class);
        var inputStream = mock(InputStream.class);
        when(resource.getInputStream()).thenReturn(inputStream);
        var parser = mock(QustionParser.class);
        CsvQuestionDao csvQuestionDao = new CsvQuestionDao(resource, parser);

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
        var parser = mock(QustionParser.class);
        CsvQuestionDao csvQuestionDao = new CsvQuestionDao(resource, parser);

        var exception = assertThrows(QuestionLoadingException.class, csvQuestionDao::getAll);

        String expectedMessage = "File is empty.";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }
}
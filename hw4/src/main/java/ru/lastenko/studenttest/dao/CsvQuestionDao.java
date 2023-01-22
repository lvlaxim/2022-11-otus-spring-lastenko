package ru.lastenko.studenttest.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import ru.lastenko.studenttest.exceptions.QuestionLoadingException;
import ru.lastenko.studenttest.i18n.LocaleHolder;
import ru.lastenko.studenttest.model.Question;
import ru.lastenko.studenttest.service.QuestionParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@RequiredArgsConstructor
public class CsvQuestionDao implements QuestionDao {

    private final Resource resource;
    private final QuestionParser<String> parser;
    private final LocaleHolder localeHolder;

    @Override
    public List<Question> getAll() {
        List<String> questionsAsStrings = getQuestionsFromResourceAsStrings();
        return parser.parseQuestionsFrom(questionsAsStrings);
    }

    private List<String> getQuestionsFromResourceAsStrings() {
        var localeWithSemicolon = localeHolder.getLocale() + ";";
        var questionsAsStrings = new ArrayList<String>();
        boolean fileIsEmpty = true;
        try (var in = resource.getInputStream();
             var reader = new BufferedReader(new InputStreamReader(in))) {
            while (true) {
                String line = reader.readLine();
                if (isNull(line)) {
                    break;
                }
                fileIsEmpty = false;
                if (line.startsWith(localeWithSemicolon)) {
                    questionsAsStrings.add(line);
                }
            }
        } catch (IOException e) {
            throw new QuestionLoadingException("Failed to load questions from csv file.", resource);
        }
        if (questionsAsStrings.isEmpty()) {
            String message;
            if (fileIsEmpty) {
                message = "The file is empty.";
            } else {
                message = String.format("The file does not contain questions for the locale %s", localeHolder.getLocale());
            }
            throw new QuestionLoadingException(message, resource);
        }
        return questionsAsStrings;
    }
}

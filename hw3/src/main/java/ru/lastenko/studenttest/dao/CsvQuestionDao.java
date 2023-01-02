package ru.lastenko.studenttest.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import ru.lastenko.studenttest.exceptions.QuestionLoadingException;
import ru.lastenko.studenttest.model.Question;
import ru.lastenko.studenttest.service.QustionParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@RequiredArgsConstructor
public class CsvQuestionDao implements QuestionDao {

    private final Resource resource;
    private final QustionParser<String> parser;
    private final String locale;

    @Override
    public List<Question> getAll() {
        List<String> questionsAsStrings = getQuestionsFromResourceAsStrings();
        return parser.parseQuestionsFrom(questionsAsStrings);
    }

    private List<String> getQuestionsFromResourceAsStrings() {
        var localeWithSemicolon = locale + ";";
        var questionsAsStrings = new ArrayList<String>();
        try (var in = resource.getInputStream();
             var reader = new BufferedReader(new InputStreamReader(in))) {
            while (true) {
                String line = reader.readLine();
                if (isNull(line)) {
                    break;
                }
                if (line.startsWith(localeWithSemicolon)) {
                    String questionAsString = line.replaceFirst(localeWithSemicolon, "");
                    questionsAsStrings.add(questionAsString);
                }
            }
        } catch (IOException e) {
            throw new QuestionLoadingException("Failed to load questions from csv file.", resource);
        }
        if (questionsAsStrings.isEmpty()) {
            throw new QuestionLoadingException("File is empty.", resource);
        }
        return questionsAsStrings;
    }
}

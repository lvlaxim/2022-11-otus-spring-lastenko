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

@RequiredArgsConstructor
public class CsvQuestionDao implements QuestionDao {

    private final Resource resource;
    private final QustionParser<String> parser;

    @Override
    public List<Question> getAll() {
        List<String> questionsAsStrings = getQuestionsFromResourceAsStrings();
        return parser.parseQuestionsFrom(questionsAsStrings);
    }

    private List<String> getQuestionsFromResourceAsStrings() {

        var questionsAsString = new ArrayList<String>();
        try (var in = resource.getInputStream();
             var reader = new BufferedReader(new InputStreamReader(in))) {
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                questionsAsString.add(line);
            }
        } catch (IOException e) {
            throw new QuestionLoadingException("Failed to load questions from csv file.", resource);
        }
        if (questionsAsString.isEmpty()) {
            throw new QuestionLoadingException("File is empty.", resource);
        }

        return questionsAsString;
    }
}

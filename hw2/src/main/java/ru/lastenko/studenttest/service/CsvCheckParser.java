package ru.lastenko.studenttest.service;

import ru.lastenko.studenttest.model.Answer;
import ru.lastenko.studenttest.model.Check;
import ru.lastenko.studenttest.model.Question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CsvCheckParser implements CheckParser<String> {
    @Override
    public Check parseCheckFrom(String checkAsString) {
        String[] checkAsStringArray = checkAsString.split(";");
        List<String> checkAsStringList = Arrays.asList(checkAsStringArray);
        String questionText = checkAsStringList.get(0);
        var question = new Question(questionText);
        var answers = new ArrayList<Answer>();
        for (int i = 1; i < checkAsStringList.size(); i += 2) {
            String answerText = checkAsStringList.get(i);
            String isAnswerCorrect = checkAsStringList.get(i + 1);
            answers.add(new Answer(answerText, Boolean.parseBoolean(isAnswerCorrect)));
        }
        return new Check(question, answers);
    }

    @Override
    public List<Check> parseChecksFrom(Collection<String> checksAsStrings) {
        List<Check> checks = checksAsStrings.stream()
                .map(this::parseCheckFrom)
                .collect(Collectors.toList());
        return checks;
    }
}

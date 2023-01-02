package ru.lastenko.studenttest.service;

import org.springframework.stereotype.Service;
import ru.lastenko.studenttest.model.AnswerOption;
import ru.lastenko.studenttest.model.Question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CsvQuestionParser implements QuestionParser<String> {
    @Override
    public Question parseQuestionFrom(String questionAsString) {
        String[] questionAsStringArray = questionAsString.split(";");
        List<String> questionAsStringList = Arrays.asList(questionAsStringArray);
        String questionText = questionAsStringList.get(0);
        var answers = new ArrayList<AnswerOption>();
        for (int i = 1; i < questionAsStringList.size(); i += 2) {
            String answerText = questionAsStringList.get(i);
            String isAnswerCorrect = questionAsStringList.get(i + 1);
            answers.add(new AnswerOption(answerText, Boolean.parseBoolean(isAnswerCorrect)));
        }
        return new Question(questionText, answers);
    }

    @Override
    public List<Question> parseQuestionsFrom(Collection<String> questionsAsStrings) {
        return questionsAsStrings.stream()
                .map(this::parseQuestionFrom)
                .collect(Collectors.toList());
    }
}

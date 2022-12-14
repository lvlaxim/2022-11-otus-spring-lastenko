package ru.lastenko.studenttest.service;

import ru.lastenko.studenttest.model.Question;

import java.util.Collection;
import java.util.List;

public interface QustionParser<T> {

    Question parseQuestionFrom(T  rawQuestion);

    List<Question> parseQuestionsFrom(Collection<T> rawQuestions);

}

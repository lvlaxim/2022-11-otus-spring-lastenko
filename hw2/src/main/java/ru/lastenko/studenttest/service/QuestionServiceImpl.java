package ru.lastenko.studenttest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lastenko.studenttest.dao.QuestionDao;
import ru.lastenko.studenttest.exceptions.QuestionLoadingException;
import ru.lastenko.studenttest.model.Question;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionDao questionDao;
    private final IOService ioService;

    @Override
    public List<Question> getAll() {
        try {
            return questionDao.getAll();
        } catch (QuestionLoadingException e) {
            ioService.outputString("Failed to load questions.");
            return Collections.emptyList();
        }
    }
}

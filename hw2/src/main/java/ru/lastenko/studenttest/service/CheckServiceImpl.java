package ru.lastenko.studenttest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lastenko.studenttest.dao.CheckDao;
import ru.lastenko.studenttest.exceptions.CheckLoadingException;
import ru.lastenko.studenttest.model.Check;
import ru.lastenko.studenttest.model.OfferedAnswer;
import ru.lastenko.studenttest.model.Question;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CheckServiceImpl implements CheckService {

    private final CheckDao checkDao;
    private final IOService ioService;

    @Override
    public List<Check> getAll() {
        try {
            return checkDao.getAll();
        } catch (CheckLoadingException e) {
            ioService.outputString("Failed to load checks.");
            return Collections.emptyList();
        }
    }

    @Override
    public void showChecks(List<Check> checks) {
        checks.forEach(this::showCheck);
    }

    @Override
    public void showCheck(Check check) {
        showQuestion(check.getQuestion());
        if (check.hasAnswerOptions()) {
            showAnswerOptions(check.getOfferedAnswers());
        } else {
            ioService.outputString("There are no options");
        }
        ioService.outputSeparateLine();
    }

    private void showQuestion(Question question) {
        ioService.outputString("Question:");
        ioService.outputString("\t" + question.getText());
    }

    private void showAnswerOptions(List<OfferedAnswer> offeredAnswers) {
        ioService.outputString("Options:");
        for (OfferedAnswer offeredAnswer : offeredAnswers) {
            ioService.outputString("\t" + offeredAnswer.getText());
        }
    }
}

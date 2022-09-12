package ru.lastenko.studenttest.service;

import org.springframework.stereotype.Service;
import ru.lastenko.studenttest.dao.CheckDao;
import ru.lastenko.studenttest.model.Check;
import lombok.RequiredArgsConstructor;
import ru.lastenko.studenttest.model.OfferedAnswer;
import ru.lastenko.studenttest.model.Question;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CheckServiceImpl implements CheckService {

    private final CheckDao checkDao;

    @Override
    public List<Check> getAll() {
        return checkDao.getAll();
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
            System.out.println("There are no options");
        }
        System.out.println();
    }

    private void showQuestion(Question question) {
        System.out.println("Question:");
        System.out.println("\t" + question.getText());
    }

    private void showAnswerOptions(List<OfferedAnswer> offeredAnswers) {
        System.out.println("Options:");
        for (OfferedAnswer offeredAnswer : offeredAnswers) {
            System.out.println("\t" + offeredAnswer.getText());
        }
    }
}

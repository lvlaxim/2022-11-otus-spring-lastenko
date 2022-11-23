package ru.lastenko.studenttest.service.modeloutput;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lastenko.studenttest.model.AnswerOption;
import ru.lastenko.studenttest.model.Check;
import ru.lastenko.studenttest.model.Question;
import ru.lastenko.studenttest.service.IOService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CheckOutputService implements ModelOutputService<Check> {

    private final ModelOutputService<Question> questionOutputService;
    private final ModelOutputService<AnswerOption> answerOptionOutputService;
    private final IOService ioService;

    @Override
    public void show(Check check) {
        var question = check.getQuestion();
        questionOutputService.show(question);
        if (check.hasAnswerOptions()) {
            showAnswerOptions(check.getAnswerOptions());
        } else {
            ioService.outputString("There are no options");
        }
        ioService.outputSeparateLine();
    }

    private void showAnswerOptions(List<AnswerOption> answerOptions) {
        ioService.outputString("Options:");
        for (AnswerOption answerOption : answerOptions) {
            answerOptionOutputService.show(answerOption);
        }
    }
}

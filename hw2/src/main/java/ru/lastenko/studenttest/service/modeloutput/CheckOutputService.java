package ru.lastenko.studenttest.service.modeloutput;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lastenko.studenttest.model.AnswerOption;
import ru.lastenko.studenttest.model.Check;
import ru.lastenko.studenttest.model.Question;
import ru.lastenko.studenttest.service.IOService;

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
            ioService.outputString("Options:");
            var answerOptions = check.getAnswerOptions();
            answerOptionOutputService.show(answerOptions);
        } else {
            ioService.outputString("There are no options");
        }
        ioService.outputSeparateLine();
    }
}

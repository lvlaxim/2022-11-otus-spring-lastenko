package ru.lastenko.studenttest.service.modeloutput;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lastenko.studenttest.model.AnswerOption;
import ru.lastenko.studenttest.model.Question;
import ru.lastenko.studenttest.service.IOService;

@Service
@RequiredArgsConstructor
public class QuestionOutputService implements ModelOutputService<Question> {

    private final ModelOutputService<AnswerOption> answerOptionOutputService;
    private final IOService ioService;

    @Override
    public void show(Question question) {
        ioService.outputString("Question:");
        ioService.outputString("\t" + question.getText());
        if (question.hasAnswerOptions()) {
            ioService.outputString("Options:");
            var answerOptions = question.getAnswerOptions();
            answerOptionOutputService.show(answerOptions);
        } else {
            ioService.outputString("There are no options");
        }
        ioService.outputSeparateLine();
    }
}

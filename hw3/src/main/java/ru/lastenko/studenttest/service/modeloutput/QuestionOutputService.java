package ru.lastenko.studenttest.service.modeloutput;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lastenko.studenttest.model.AnswerOption;
import ru.lastenko.studenttest.model.Question;
import ru.lastenko.studenttest.service.CommunicationService;

@Service
@RequiredArgsConstructor
public class QuestionOutputService implements ModelOutputService<Question> {

    private final ModelOutputService<AnswerOption> answerOptionOutputService;
    private final CommunicationService communicationService;

    @Override
    public void show(Question question) {
        communicationService.showMessageByCode("question");
        communicationService.showMessage("\t" + question.getText());
        if (question.hasAnswerOptions()) {
            communicationService.showMessageByCode("options");
            var answerOptions = question.getAnswerOptions();
            answerOptionOutputService.show(answerOptions);
        } else {
            communicationService.showMessageByCode("no.options");
        }
        communicationService.showSeparateLine();
    }
}

package ru.lastenko.studenttest.service.modeloutput;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lastenko.studenttest.model.AnswerOption;
import ru.lastenko.studenttest.service.CommunicationService;

@Service
@RequiredArgsConstructor
public class AnswerOptionOutputService implements ModelOutputService<AnswerOption> {

    private final CommunicationService communicationService;

    @Override
    public void show(AnswerOption answerOption) {
        communicationService.showMessage("\t" + answerOption.getText());
    }
}

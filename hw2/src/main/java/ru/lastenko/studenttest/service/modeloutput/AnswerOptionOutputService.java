package ru.lastenko.studenttest.service.modeloutput;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lastenko.studenttest.model.AnswerOption;
import ru.lastenko.studenttest.service.IOService;

@Service
@RequiredArgsConstructor
public class AnswerOptionOutputService implements ModelOutputService<AnswerOption> {

    private final IOService ioService;

    @Override
    public void show(AnswerOption answerOption) {
        ioService.outputString("\t" + answerOption.getText());
    }
}

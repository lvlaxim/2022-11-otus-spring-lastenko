package ru.lastenko.studenttest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.lastenko.studenttest.config.ApplicationProperties;

import java.util.List;
import java.util.Locale;


@Service
@RequiredArgsConstructor
public class CommunicationServiceImpl implements CommunicationService {

    private final MessageSource messageSource;
    private final ApplicationProperties applicationProperties;
    private final IOService ioService;

    @Override
    public void showMessageByCode(String messageCode, Object... args) {
        Locale locale = applicationProperties.getLocale();
        String message = messageSource.getMessage(messageCode, args, locale);
        ioService.outputString(message);
    }

    @Override
    public void showMessage(String message) {
        ioService.outputString(message);
    }

    @Override
    public void showSeparateLine() {
        ioService.outputSeparateLine();
    }

    @Override
    public String showMessageByCodeAndGetFeedback(String messageCode, Object... args) {
        showMessageByCode(messageCode, args);
        String feedback = ioService.readString();
        showSeparateLine();
        return feedback;
    }

    @Override
    public List<String> showMessageByCodeAndGetFeedbackAsList(String messageCode, Object... args) {
        String feedback = showMessageByCodeAndGetFeedback(messageCode, args);
        String[] feedbackAsArray = feedback.split("\\s*,\\s*");
        return List.of(feedbackAsArray);
    }
}
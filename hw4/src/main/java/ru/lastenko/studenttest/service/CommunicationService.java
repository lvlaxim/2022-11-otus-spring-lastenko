package ru.lastenko.studenttest.service;

import java.util.Set;

public interface CommunicationService {

    void showMessageByCode(String messageCode, Object... args);

    void showMessage(String message);

    void showSeparateLine();

    String showMessageByCodeAndGetFeedback(String messageCode, Object... args);

    Set<String> showMessageByCodeAndGetFeedbackAsList(String messageCode, Object... args);
}

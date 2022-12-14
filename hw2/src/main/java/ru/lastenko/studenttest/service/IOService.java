package ru.lastenko.studenttest.service;

import java.util.List;

public interface IOService {

    void outputString(String s);

    void outputSeparateLine();

    String readStringWithPrompt(String prompt);

    List<String> readAndSplitStringByCommasWithPrompt(String prompt);
}

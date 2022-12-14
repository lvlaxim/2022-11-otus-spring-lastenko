package ru.lastenko.studenttest.service;

import lombok.RequiredArgsConstructor;

import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

@RequiredArgsConstructor
public class IOServiceStreams implements IOService {

    private final PrintStream output;
    private final Scanner input;

    @Override
    public void outputString(String s) {
        output.println(s);
    }

    @Override
    public void outputSeparateLine() {
        output.println();
    }

    @Override
    public String readStringWithPrompt(String prompt) {
        outputString(prompt);
        var string = input.nextLine();
        outputSeparateLine();
        return string;
    }

    @Override
    public List<String> readAndSplitStringByCommasWithPrompt(String prompt) {
        var string = readStringWithPrompt(prompt);
        String[] stringAsArray = string.split("\\s*,\\s*");
        return List.of(stringAsArray);
    }
}

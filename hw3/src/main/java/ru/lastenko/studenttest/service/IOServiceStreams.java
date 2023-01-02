package ru.lastenko.studenttest.service;

import lombok.RequiredArgsConstructor;

import java.io.PrintStream;
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
    public String readString() {
        return input.nextLine();
    }

}

package ru.lastenko.jpalibrary.service;

import lombok.RequiredArgsConstructor;
import ru.lastenko.jpalibrary.service.tostringconvertion.ToStringConversionHandler;

import java.io.PrintStream;
import java.util.Collection;
import java.util.Scanner;

@RequiredArgsConstructor
public class IOServiceStreams implements IOService {

    private final PrintStream output;

    private final Scanner input;

    private final ToStringConversionHandler toStringConversionHandler;

    @Override
    public void outputString(String s) {
        output.println(s);
    }

    @Override
    public void output(Object object) {
        String objectAsString = toStringConversionHandler.convertToString(object);
        outputString(objectAsString);
    }

    @Override
    public void output(Collection<?> objects) {
        String objectAsString = toStringConversionHandler.convertToString(objects);
        outputString(objectAsString);
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
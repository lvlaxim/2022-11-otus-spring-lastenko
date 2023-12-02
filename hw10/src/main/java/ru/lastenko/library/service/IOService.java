package ru.lastenko.library.service;

import java.util.Collection;

public interface IOService {

    void outputString(String s);

    void output(Object object);

    void output(Collection<?> objects);

    void outputSeparateLine();

    String readString();
}
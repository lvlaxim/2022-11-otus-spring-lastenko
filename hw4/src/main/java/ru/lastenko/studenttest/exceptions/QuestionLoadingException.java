package ru.lastenko.studenttest.exceptions;

import org.springframework.core.io.Resource;

import java.util.MissingResourceException;

public class QuestionLoadingException extends MissingResourceException {

    public QuestionLoadingException(String message, Resource resource) {
        super(message, resource.getClass().getName(), resource.getFilename());
    }
}

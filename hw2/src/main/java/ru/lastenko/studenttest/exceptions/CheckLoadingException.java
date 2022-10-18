package ru.lastenko.studenttest.exceptions;

import org.springframework.core.io.Resource;

import java.util.MissingResourceException;

public class CheckLoadingException extends MissingResourceException {

    public CheckLoadingException(String message, Resource resource) {
        super(message, resource.getClass().getName(), resource.getFilename());
    }
}

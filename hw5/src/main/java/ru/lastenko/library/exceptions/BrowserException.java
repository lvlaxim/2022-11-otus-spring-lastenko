package ru.lastenko.library.exceptions;

public class BrowserException extends RuntimeException {
    public BrowserException(Throwable cause) {
        super("Failed to open page in browser.", cause);
    }
}

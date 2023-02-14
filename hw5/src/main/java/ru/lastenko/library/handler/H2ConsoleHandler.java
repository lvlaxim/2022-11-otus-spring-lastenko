package ru.lastenko.library.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.lastenko.library.exceptions.BrowserException;


@Service
@RequiredArgsConstructor
public class H2ConsoleHandler implements DbConsoleHandler {

    public static final String H2_CONSOLE_URI = "http://localhost:8080/h2-console";
    @Value("${spring.datasource.url}")
    private final String url;
    private final BrowserHandler browserHandler;

    @Override
    public String openConsoleInBrowserAndGetUrlMsg() {
        String sucessMessage = "Консоль базы данных открыта.";
        String message = "Используйте JDBC URL: ";
        try {
            browserHandler.browse(H2_CONSOLE_URI);
        } catch (BrowserException e) {
            String errorMessage = String.format("Не удалось открыть H2-консоль автоматически. Перейдите по ссылке %s",
                    H2_CONSOLE_URI);
            return errorMessage + System.lineSeparator() + message + url;
        }
        return sucessMessage + System.lineSeparator() + message + url;
    }


}

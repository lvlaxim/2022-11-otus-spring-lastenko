package ru.lastenko.library.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.lastenko.library.handler.DbConsoleHandler;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommands {

    private final DbConsoleHandler dbConsoleHandler;

    @ShellMethod(value = "Open DB console.", key = {"dbc"})
    public String showDbConsole() {
        return dbConsoleHandler.openConsoleInBrowserAndGetUrlMsg();
    }

}
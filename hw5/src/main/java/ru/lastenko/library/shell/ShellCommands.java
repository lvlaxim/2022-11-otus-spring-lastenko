package ru.lastenko.library.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.lastenko.library.handler.DbConsoleHandler;
import ru.lastenko.library.service.AuthorService;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommands {

    private final DbConsoleHandler dbConsoleHandler;
    private final AuthorService authorService;

    @ShellMethod(value = "Open DB console.", key = {"dbc"})
    public String showDbConsole() {
        return dbConsoleHandler.openConsoleInBrowserAndGetUrlMsg();
    }

    @ShellMethod(value = "Show all authors.", key = {"a"})
    public String showAllAuthors() {
        return authorService.getAll().toString();
    }

}
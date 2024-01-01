package ru.lastenko.springbatch.shell;

import lombok.RequiredArgsConstructor;
import org.h2.tools.Console;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.sql.SQLException;

@ShellComponent
@RequiredArgsConstructor
public class MigrationCommands {

    private final MigrationCommandsProcessor migrationCommandsProcessor;

    @SuppressWarnings("unused")
    @ShellMethod(value = "Open DB console.", key = {"dbc"})
    public String openDbConsole() throws SQLException {
        Console.main();
        return "H2-консоль открыта.";
    }


    @SuppressWarnings("unused")
    @ShellMethod(value = "Start library migration", key = "sm")
    public String startMigrationJobWithJobOperator() throws Exception {
        return migrationCommandsProcessor.startMigrateLibraryFromSqlDbToMongoDb();
    }

}

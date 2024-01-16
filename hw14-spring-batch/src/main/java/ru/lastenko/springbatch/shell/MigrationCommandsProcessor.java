package ru.lastenko.springbatch.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobExecutionException;
import org.springframework.stereotype.Component;

import java.util.Properties;

import static ru.lastenko.springbatch.config.LibraryMigrationJobConfig.LIBRARY_MIGRATION_JOB;

@Component
@RequiredArgsConstructor
public class MigrationCommandsProcessor {

    private final JobOperator jobOperator;

    public String startMigrateLibraryFromSqlDbToMongoDb() throws NoSuchJobExecutionException {
        Properties properties = new Properties();
        long executionId;
        try {
            executionId = jobOperator.start(LIBRARY_MIGRATION_JOB, properties);
        } catch (JobExecutionException e) {
            return String.format("Не удалось запустить миграцию. Подробности: '%s'", e.getMessage());
        }
        return String.format("Миграция выполнена! Подробности: %s", jobOperator.getSummary(executionId));
    }

}

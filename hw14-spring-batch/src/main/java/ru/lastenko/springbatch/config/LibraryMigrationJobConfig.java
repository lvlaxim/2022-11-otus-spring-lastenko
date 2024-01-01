package ru.lastenko.springbatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.lastenko.springbatch.registry.IdMigrationRegistry;
import ru.lastenko.springbatch.registry.InMemoryIdMigrationRegistry;


@SuppressWarnings("unused")
@Configuration
public class LibraryMigrationJobConfig {

    @Value("${app.chunk-size}")
    public static final int CHUNK_SIZE = 5;

    public static final String LIBRARY_MIGRATION_JOB = "libraryMigrationJob";

    @Bean
    public IdMigrationRegistry bookIdMigrationRegistry() {
        return new InMemoryIdMigrationRegistry();
    }

    @Bean
    public IdMigrationRegistry authorIdMigrationRegistry() {
        return new InMemoryIdMigrationRegistry();
    }

    @Bean
    public IdMigrationRegistry genreIdMigrationRegistry() {
        return new InMemoryIdMigrationRegistry();
    }

    @Bean
    public Job importUserJob(JobRepository jobRepository,
                             Step authorsMigrationStep,
                             Step genresMigrationStep,
                             Step booksMigrationStep,
                             Step commentsMigrationStep) {
        return new JobBuilder(LIBRARY_MIGRATION_JOB, jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(authorsMigrationStep)
                .next(genresMigrationStep)
                .next(booksMigrationStep)
                .next(commentsMigrationStep)
                .end()
                .build();
    }


}

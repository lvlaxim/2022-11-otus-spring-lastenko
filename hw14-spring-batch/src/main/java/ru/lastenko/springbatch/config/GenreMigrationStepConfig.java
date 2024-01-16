package ru.lastenko.springbatch.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import ru.lastenko.jpalibrary.model.Genre;
import ru.lastenko.springbatch.domain.MongoGenre;
import ru.lastenko.springbatch.mapper.GenreMapper;

import static ru.lastenko.springbatch.config.LibraryMigrationJobConfig.CHUNK_SIZE;

@Configuration
public class GenreMigrationStepConfig {


    @Bean
    public Step genresMigrationStep(JobRepository jobRepository,
                                    PlatformTransactionManager platformTransactionManager,
                                    ItemReader<Genre> reader,
                                    ItemProcessor<Genre, MongoGenre> processor,
                                    ItemWriter<MongoGenre> writer) {
        return new StepBuilder("genresMigrationStep", jobRepository)
                .<Genre, MongoGenre>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public ItemReader<Genre> genreReader(EntityManagerFactory entityManagerFactory) {
        return new JpaCursorItemReaderBuilder<Genre>()
                .name("genreItemReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("from Genre")
                .build();
    }

    @Bean
    public ItemProcessor<Genre, MongoGenre> genreProcessor(GenreMapper genreMapper) {
        return genreMapper::map;
    }

    @Bean
    public ItemWriter<MongoGenre> genreWriter(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<MongoGenre>()
                .template(mongoTemplate)
                .collection("genres")
                .build();
    }
}

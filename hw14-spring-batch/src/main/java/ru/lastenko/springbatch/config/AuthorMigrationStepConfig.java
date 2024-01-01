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
import ru.lastenko.jpalibrary.model.Author;
import ru.lastenko.springbatch.domain.MongoAuthor;
import ru.lastenko.springbatch.mapper.AuthorMapper;

import static ru.lastenko.springbatch.config.LibraryMigrationJobConfig.CHUNK_SIZE;

@Configuration
public class AuthorMigrationStepConfig {

    @Bean
    public Step authorsMigrationStep(JobRepository jobRepository,
                                     PlatformTransactionManager platformTransactionManager,
                                     ItemReader<Author> reader,
                                     ItemProcessor<Author, MongoAuthor> processor,
                                     ItemWriter<MongoAuthor> writer) {
        return new StepBuilder("authorsMigrationStep", jobRepository)
                .<Author, MongoAuthor>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public ItemReader<Author> authorReader(EntityManagerFactory entityManagerFactory) {
        return new JpaCursorItemReaderBuilder<Author>()
                .name("authorItemReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("from Author")
                .build();
    }

    @Bean
    public ItemProcessor<Author, MongoAuthor> authorProcessor(AuthorMapper authorMapper) {
        return authorMapper::map;
    }

    @Bean
    public ItemWriter<MongoAuthor> authorWriter(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<MongoAuthor>()
                .template(mongoTemplate)
                .collection("authors")
                .build();
    }
}

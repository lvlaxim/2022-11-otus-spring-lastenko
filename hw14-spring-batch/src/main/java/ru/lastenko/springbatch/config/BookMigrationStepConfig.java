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
import ru.lastenko.jpalibrary.model.Book;
import ru.lastenko.springbatch.domain.MongoBook;
import ru.lastenko.springbatch.mapper.BookMapper;

import static ru.lastenko.springbatch.config.LibraryMigrationJobConfig.CHUNK_SIZE;

@Configuration
public class BookMigrationStepConfig {

    @Bean
    public Step booksMigrationStep(JobRepository jobRepository,
                                   PlatformTransactionManager platformTransactionManager,
                                   ItemReader<Book> reader,
                                   ItemProcessor<Book, MongoBook> itemProcessor,
                                   ItemWriter<MongoBook> writer) {
        return new StepBuilder("booksMigrationStep", jobRepository)
                .<Book, MongoBook>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(reader)
                .processor(itemProcessor)
                .writer(writer)
                .build();
    }

    @Bean
    public ItemReader<Book> bookReader(EntityManagerFactory entityManagerFactory) {
        return new JpaCursorItemReaderBuilder<Book>()
                .name("bookItemReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("from Book b " +
                        "left join fetch b.author " +
                        "left join fetch b.genre")
                .build();
    }

    @Bean
    public ItemProcessor<Book, MongoBook> bookProcessor(BookMapper bookMapper) {
        return bookMapper::map;
    }

    @Bean
    public ItemWriter<MongoBook> bookWriter(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<MongoBook>()
                .template(mongoTemplate)
                .collection("books")
                .build();
    }

}

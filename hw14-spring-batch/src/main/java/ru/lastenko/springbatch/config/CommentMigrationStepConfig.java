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
import ru.lastenko.jpalibrary.model.Comment;
import ru.lastenko.springbatch.domain.MongoComment;
import ru.lastenko.springbatch.mapper.CommentMapper;

import static ru.lastenko.springbatch.config.LibraryMigrationJobConfig.CHUNK_SIZE;

@Configuration
public class CommentMigrationStepConfig {

    @Bean
    public Step commentsMigrationStep(JobRepository jobRepository,
                                      PlatformTransactionManager platformTransactionManager,
                                      ItemReader<Comment> reader,
                                      ItemProcessor<Comment, MongoComment> processor,
                                      ItemWriter<MongoComment> writer) {
        return new StepBuilder("commentsMigrationStep", jobRepository)
                .<Comment, MongoComment>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public ItemReader<Comment> commentReader(EntityManagerFactory entityManagerFactory) {
        return new JpaCursorItemReaderBuilder<Comment>()
                .name("commentItemReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("from Comment")
                .build();
    }

    @Bean
    public ItemProcessor<Comment, MongoComment> commentProcessor(CommentMapper commentMapper) {
        return commentMapper::map;
    }

    @Bean
    public ItemWriter<MongoComment> commentWriter(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<MongoComment>()
                .template(mongoTemplate)
                .collection("comments")
                .build();
    }
}

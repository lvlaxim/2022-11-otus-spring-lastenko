package ru.lastenko.studenttest.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import ru.lastenko.studenttest.dao.CsvQuestionDao;
import ru.lastenko.studenttest.dao.QuestionDao;
import ru.lastenko.studenttest.service.QustionParser;

@Configuration
@PropertySource("classpath:application.properties")
public class DaoConfig {

    @Bean
    public QuestionDao questionDao(ResourceLoader resourceLoader,
                                   QustionParser<String> parser,
                                   @Value("${file}") String questionsLocation) {
        Resource resource = resourceLoader.getResource(questionsLocation);
        return new CsvQuestionDao(resource, parser);
    }
}

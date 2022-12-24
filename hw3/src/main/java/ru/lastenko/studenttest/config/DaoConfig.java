package ru.lastenko.studenttest.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import ru.lastenko.studenttest.dao.CsvQuestionDao;
import ru.lastenko.studenttest.dao.QuestionDao;
import ru.lastenko.studenttest.service.QustionParser;

@Configuration
@EnableConfigurationProperties(ApplicationProperties.class)
public class DaoConfig {

    @Bean
    public QuestionDao questionDao(ResourceLoader resourceLoader,
                                   QustionParser<String> parser,
                                   ApplicationProperties applicationProperties) {
        String fileName = applicationProperties.getFile();
        Resource resource = resourceLoader.getResource(fileName);
        return new CsvQuestionDao(resource, parser);
    }
}

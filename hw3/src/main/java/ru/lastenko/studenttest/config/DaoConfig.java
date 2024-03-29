package ru.lastenko.studenttest.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import ru.lastenko.studenttest.dao.CsvQuestionDao;
import ru.lastenko.studenttest.dao.QuestionDao;
import ru.lastenko.studenttest.service.QuestionParser;

import java.util.Locale;

@Configuration
@EnableConfigurationProperties(ApplicationProperties.class)
public class DaoConfig {

    @Bean
    public QuestionDao questionDao(ResourceLoader resourceLoader,
                                   QuestionParser<String> parser,
                                   ApplicationProperties applicationProperties) {
        String fileName = applicationProperties.getFile();
        Resource resource = resourceLoader.getResource(fileName);
        Locale locale = applicationProperties.getLocale();
        return new CsvQuestionDao(resource, parser, locale.toString());
    }
}

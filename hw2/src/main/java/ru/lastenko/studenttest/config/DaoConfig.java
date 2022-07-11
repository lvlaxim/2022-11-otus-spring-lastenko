package ru.lastenko.studenttest.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import ru.lastenko.studenttest.dao.CheckDao;
import ru.lastenko.studenttest.dao.CsvCheckDao;
import ru.lastenko.studenttest.service.CheckParser;

@Configuration
@PropertySource("classpath:application.properties")
public class DaoConfig {

    @Value("${file}")
    private String questionsLocation;

    @Bean
    public CheckDao checkDao(ResourceLoader resourceLoader, CheckParser<String> parser) {
        Resource resource = resourceLoader.getResource(questionsLocation);
        return new CsvCheckDao(resource, parser);
    }
}

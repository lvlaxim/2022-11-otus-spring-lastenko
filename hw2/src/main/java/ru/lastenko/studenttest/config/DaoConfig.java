package ru.lastenko.studenttest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import ru.lastenko.studenttest.dao.CheckDao;
import ru.lastenko.studenttest.dao.CsvCheckDao;
import ru.lastenko.studenttest.service.CheckParser;

@Configuration
public class DaoConfig {

    @Bean
    public CheckDao checkDao(ResourceLoader resourceLoader , CheckParser<String> parser) {
        Resource resource = resourceLoader.getResource("questions.csv");
        return new CsvCheckDao(resource, parser);
    }
}

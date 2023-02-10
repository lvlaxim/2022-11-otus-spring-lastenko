package ru.lastenko.studenttest.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import ru.lastenko.studenttest.dao.CsvQuestionDao;
import ru.lastenko.studenttest.dao.QuestionDao;
import ru.lastenko.studenttest.service.IOService;
import ru.lastenko.studenttest.service.IOServiceStreams;
import ru.lastenko.studenttest.service.QuestionParser;

import java.util.Scanner;

@Configuration
@EnableConfigurationProperties(ApplicationProperties.class)
public class ApplicationConfig {

    @Bean
    public QuestionDao questionDao(ApplicationProperties applicationProperties,
                                   ResourceLoader resourceLoader,
                                   QuestionParser<String> parser) {
        String fileName = applicationProperties.getFile();
        Resource resource = resourceLoader.getResource(fileName);
        return new CsvQuestionDao(resource, parser, applicationProperties);
    }

    @Bean
    public IOService ioService() {
        var scanner = new Scanner(System.in);
        return new IOServiceStreams(System.out, scanner);
    }
}

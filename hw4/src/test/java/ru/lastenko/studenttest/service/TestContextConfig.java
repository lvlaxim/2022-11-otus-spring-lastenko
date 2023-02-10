package ru.lastenko.studenttest.service;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import ru.lastenko.studenttest.config.ApplicationProperties;

import java.util.Locale;

@SpringBootConfiguration
@EnableConfigurationProperties(ApplicationProperties.class)
@ComponentScan({"ru.lastenko.studenttest.service"})
public class TestContextConfig {

}

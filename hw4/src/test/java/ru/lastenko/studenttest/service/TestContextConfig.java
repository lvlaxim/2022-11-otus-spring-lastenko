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

    // без этого не удалось получить мок вместо MessageSource в CommunicationServiceImplTest
    @Bean
    public MessageSource messageSource() {
        return new MessageSource() {
            @Override
            public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
                return null;
            }

            @Override
            public String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException {
                return null;
            }

            @Override
            public String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException {
                return null;
            }
        };
    }
}

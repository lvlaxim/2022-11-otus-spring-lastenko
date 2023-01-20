package ru.lastenko.studenttest.config;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;

@ConfigurationProperties(prefix = "application")
@Getter
@Setter
@Accessors(chain = true)
public class ApplicationProperties implements LocaleProvider {

    private String file;
    private int threshold = 3;
    private Locale locale;
}
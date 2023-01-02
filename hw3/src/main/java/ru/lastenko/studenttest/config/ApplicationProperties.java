package ru.lastenko.studenttest.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;

@ConfigurationProperties(prefix = "application")
@Getter
@Setter
public class ApplicationProperties {

    private String file;
    private int threshold = 3;
    private Locale locale;
}
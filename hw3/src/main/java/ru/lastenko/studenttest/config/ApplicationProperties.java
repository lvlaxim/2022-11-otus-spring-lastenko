package ru.lastenko.studenttest.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application")
@Getter
@Setter
public class ApplicationProperties {

    private String file;
    private int threshold = 3;
}
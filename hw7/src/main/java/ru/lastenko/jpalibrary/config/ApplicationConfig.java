package ru.lastenko.jpalibrary.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.lastenko.jpalibrary.service.IOService;
import ru.lastenko.jpalibrary.service.IOServiceStreams;
import ru.lastenko.jpalibrary.service.tostringconvertion.ToStringConversionHandler;

import java.util.Scanner;

@Configuration
public class ApplicationConfig {

    @Bean
    @SuppressWarnings("java:S106")
    public IOService ioService(ToStringConversionHandler toStringConversionHandler) {
        var scanner = new Scanner(System.in);
        return new IOServiceStreams(System.out, scanner, toStringConversionHandler);
    }
}

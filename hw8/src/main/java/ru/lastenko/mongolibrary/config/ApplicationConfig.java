package ru.lastenko.mongolibrary.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.lastenko.mongolibrary.service.IOService;
import ru.lastenko.mongolibrary.service.IOServiceStreams;
import ru.lastenko.mongolibrary.service.tostringconvertion.ToStringConversionHandler;

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

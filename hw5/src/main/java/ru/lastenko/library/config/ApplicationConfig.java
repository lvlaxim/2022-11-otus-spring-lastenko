package ru.lastenko.library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.lastenko.library.service.IOService;
import ru.lastenko.library.service.IOServiceStreams;
import ru.lastenko.library.service.tostringconvertion.ToStringConversionHandler;

import java.util.Scanner;

@Configuration
public class ApplicationConfig {

    @Bean
    public IOService ioService(ToStringConversionHandler toStringConversionHandler) {
        var scanner = new Scanner(System.in);
        return new IOServiceStreams(System.out, scanner, toStringConversionHandler);
    }
}

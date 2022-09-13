package ru.lastenko.studenttest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.lastenko.studenttest.service.IOService;
import ru.lastenko.studenttest.service.IOServiceStreams;

import java.util.Scanner;

@Configuration
public class IoServiceConfig {

    @Bean
    public IOService ioService() {
        var scanner = new Scanner(System.in);
        return new IOServiceStreams(System.out, scanner);
    }

}

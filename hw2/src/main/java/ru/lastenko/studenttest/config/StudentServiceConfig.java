package ru.lastenko.studenttest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.lastenko.studenttest.service.StudentService;
import ru.lastenko.studenttest.service.ConsoleStudentService;

import java.util.Scanner;

@Configuration
public class StudentServiceConfig {

    @Bean
    public StudentService studentService() {
        return new ConsoleStudentService(new Scanner(System.in));
    }
}

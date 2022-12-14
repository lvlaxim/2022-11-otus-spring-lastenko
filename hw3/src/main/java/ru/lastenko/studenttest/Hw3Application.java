package ru.lastenko.studenttest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.lastenko.studenttest.service.AppRunner;

@SpringBootApplication
public class Hw3Application {

    public static void main(String[] args) {
//        SpringApplication.run(Hw3Application.class, args);

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Hw3Application.class);
        var appRunner = context.getBean(AppRunner.class);
        appRunner.executeExam();
    }

}

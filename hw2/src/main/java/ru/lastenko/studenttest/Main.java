package ru.lastenko.studenttest;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ru.lastenko.studenttest.service.AppRunner;

//@Configuration - не нужен здесь?
@ComponentScan
public class Main {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        var appRunner = context.getBean(AppRunner.class);
        appRunner.executeExam();

    }
}

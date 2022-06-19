package ru.lastenko.studenttest;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.lastenko.studenttest.service.Examiner;

public class Main {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        var examiner = context.getBean(Examiner.class);
        examiner.showAllChecks();

    }
}

package ru.lastenko.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.lastenko.spring.service.Examiner;

public class Main {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        var examiner = context.getBean(Examiner.class);
        examiner.showAllChecks();

    }
}

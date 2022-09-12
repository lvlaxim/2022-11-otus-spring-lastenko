package ru.lastenko.studenttest.service;

import lombok.RequiredArgsConstructor;
import ru.lastenko.studenttest.model.Student;

import java.util.List;
import java.util.Scanner;

@RequiredArgsConstructor
public class ConsoleStudentService implements StudentService {

    private final Scanner studentInput;

    @Override
    public void showStudentResult(Student student) {
        var result = String.format("%s, your score is %d", student.getName(), student.getScore());
        System.out.println(result);
    }

    @Override
    public Student getStudent() {
        String name;
        String surname;
        System.out.println("Hi! please introduce yourself");
        System.out.println("your name?");
        name = studentInput.nextLine();
        System.out.println("your surname?");
        surname = studentInput.nextLine();
        return new Student(name, surname);
    }

    @Override
    public List<String> getStudentAnswers() {
        String studentAnswer;
        System.out.println("Please enter your answers separated by commas");
        studentAnswer = studentInput.nextLine();
        System.out.println();
        String[] studentAnswerAsArray = studentAnswer.split("\\s*,\\s*");
        return List.of(studentAnswerAsArray);
    }
}

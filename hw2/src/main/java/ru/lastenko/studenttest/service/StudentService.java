package ru.lastenko.studenttest.service;

import ru.lastenko.studenttest.model.Student;

import java.util.List;

public interface StudentService {

    void showStudentResult(Student student);

    Student getStudent();

    List<String> getStudentAnswers();
}

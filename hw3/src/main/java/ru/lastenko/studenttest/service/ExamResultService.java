package ru.lastenko.studenttest.service;

import ru.lastenko.studenttest.model.ExamResult;
import ru.lastenko.studenttest.model.Student;

public interface ExamResultService {

    ExamResult summarizeResult(Student student, int score);

}

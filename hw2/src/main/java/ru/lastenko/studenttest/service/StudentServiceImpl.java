package ru.lastenko.studenttest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lastenko.studenttest.model.Student;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final IOService ioService;

    @Override
    public Student determineCurrentStudent() {
        String name;
        String surname;
        ioService.outputString("Hi! please introduce yourself");
        name = ioService.readStringWithPrompt("your name?");
        surname = ioService.readStringWithPrompt("your surname?");
        return new Student(name, surname);
    }

}

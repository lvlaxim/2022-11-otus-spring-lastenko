package ru.lastenko.studenttest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lastenko.studenttest.model.Student;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final CommunicationService communicationService;

    @Override
    public Student determineCurrentStudent() {
        askToIntroduce();
        String name = getPersonalDataWithPrompt("prompt.name");
        String surname = getPersonalDataWithPrompt("prompt.surname");
        return new Student(name, surname);
    }

    private void askToIntroduce() {
        communicationService.showMessageByCode("prompt.introduce");
    }

    private String getPersonalDataWithPrompt(String messageCode) {
        return communicationService.showMessageByCodeAndGetFeedback(messageCode);
    }

}

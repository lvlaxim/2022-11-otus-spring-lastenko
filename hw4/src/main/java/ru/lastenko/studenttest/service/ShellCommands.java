package ru.lastenko.studenttest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommands {

    private final ExamExecutor examExecutor;

    @ShellMethod(key = {"start", "s", "e"}, value = "Start an exam.")
    public void startExam() {
        examExecutor.executeExam();
    }
}
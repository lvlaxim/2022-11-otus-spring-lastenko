package ru.lastenko.studenttest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.lastenko.studenttest.i18n.LocaleToggle;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommands {

    private final ExamExecutor examExecutor;
    private final LocaleToggle localeToggle;

    @ShellMethod(key = {"start", "s", "e"}, value = "Start an exam.")
    public void startExam() {
        examExecutor.executeExam();
    }

    @ShellMethod(key = {"locale", "l"}, value = "Show current locale.")
    public void showCurrentLocale() {
        localeToggle.showCurrentLocale();
    }

    @ShellMethod(key = {"set locale", "sl"}, value = "Select locale.")
    public void setLocale(@ShellOption() String locale) {
        localeToggle.setLocale(locale);
    }
}
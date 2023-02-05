package ru.lastenko.studenttest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.lastenko.studenttest.i18n.LocaleToggle;

import static org.springframework.shell.Availability.available;
import static org.springframework.shell.Availability.unavailable;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommands {

    private final ExamExecutor examExecutor;
    private final LocaleToggle localeToggle;
    private boolean isReady = false;

    @ShellMethod(value = "Confirm of readiness for the test.", key = {"ready", "r"})
    public void confirmReadiness() {
        isReady = true;
    }

    @ShellMethod(value = "Start an exam.", key = {"start", "s", "e"})
    @ShellMethodAvailability(value = "isReadinessConfirmed")
    public void startExam() {
        examExecutor.executeExam();
    }

    @ShellMethod(value = "Show current locale.", key = {"locale", "l"})
    public void showCurrentLocale() {
        localeToggle.showCurrentLocale();
    }

    @ShellMethod(value = "Select locale.", key = {"set locale", "sl"})
    public void setLocale(@ShellOption() String locale) {
        localeToggle.setLocale(locale);
    }

    private Availability isReadinessConfirmed() {
        String message = "it is required to confirm readiness for the test.";
        return isReady ? available() : unavailable(message);
    }
}
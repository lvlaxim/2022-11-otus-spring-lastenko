package ru.lastenko.studenttest.i18n;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.LocaleUtils;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;
import ru.lastenko.studenttest.service.CommunicationService;

import java.util.*;

@Service
@RequiredArgsConstructor
public class LocaleToggleImpl implements LocaleToggle {

    private final LocaleHolder localeHolder;
    private final CommunicationService communicationService;

    @Override
    public void setLocale(String localeAsString) {
        Locale currentLocale = localeHolder.getLocale();
        try {
            Locale newLocale = LocaleUtils.toLocale(localeAsString);
            localeHolder.setLocale(newLocale);
            communicationService.showMessageByCode("locale", newLocale);
        } catch (NoSuchMessageException | IllegalArgumentException e) {
            localeHolder.setLocale(currentLocale);
            communicationService.showMessageByCode("locale.unsupported", localeAsString);
            showCurrentLocale();
        }
    }

    @Override
    public void showCurrentLocale() {
        communicationService.showMessageByCode("locale", localeHolder.getLocale());
    }
}

package ru.lastenko.studenttest.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.lastenko.studenttest.config.ApplicationProperties;
import ru.lastenko.studenttest.dao.QuestionDao;
import ru.lastenko.studenttest.i18n.LocaleToggle;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@DisplayName("Сервис для общения с пользователем")
@SpringBootTest
@ExtendWith(SpringExtension.class)
class CommunicationServiceImplTest {

    @MockBean
    private MessageSource messageSource;
    @MockBean
    private ApplicationProperties applicationProperties;
    @MockBean
    private IOService ioService;
    @MockBean
    private QuestionDao questionDao;
    @MockBean
    private LocaleToggle localeToggle;
    @Autowired
    private CommunicationServiceImpl communicationService;

    @DisplayName("должен показать сообщение и вернуть обратную связь от пользователя в виде списка, " +
            "разделив его сообщение по запятым")
    @ParameterizedTest(name = "ввод пользователя: \"{0}\"")
    @ValueSource(strings = {"sting1,string2", "sting1 , string2", "sting1   ,   string2"})
    void showMessageByCodeAndGetFeedbackAsList(String string) {
        when(ioService.readString()).thenReturn(string);
        var messageCode = "message.code";

        Set<String> actualStringList = communicationService.showMessageByCodeAndGetFeedbackAsList(messageCode);

        verify(applicationProperties, times(1)).getLocale();
        verify(messageSource, times(1)).getMessage(eq(messageCode), any(), any());
        verify(ioService, times(1)).outputString(any());
        verify(ioService, times(1)).readString();
        var expectedStingList = Set.of("sting1", "string2");
        assertThat(actualStringList)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(expectedStingList);
    }
}
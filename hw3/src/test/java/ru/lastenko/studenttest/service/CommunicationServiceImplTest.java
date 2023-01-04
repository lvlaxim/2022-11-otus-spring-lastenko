package ru.lastenko.studenttest.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import ru.lastenko.studenttest.config.ApplicationProperties;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@DisplayName("Сервис для общения с пользователем")
@ExtendWith(MockitoExtension.class)
class CommunicationServiceImplTest {

    @Mock
    private MessageSource messageSource;
    @Mock
    private ApplicationProperties applicationProperties;
    @Mock
    private IOService ioService;

    @DisplayName("должен показать сообщение и вернуть обратную связь от пользователя в виде списка, " +
            "разделив его сообщение по запятым")
    @ParameterizedTest(name = "ввод пользователя: \"{0}\"")
    @ValueSource(strings = {"sting1,string2", "sting1 , string2", "sting1   ,   string2"})
    void showMessageByCodeAndGetFeedbackAsList(String string) {
        when(ioService.readString()).thenReturn(string);
        var communicationService = new CommunicationServiceImpl(messageSource, applicationProperties, ioService);
        var messageCode = "message.code";

        List<String> actualStringList = communicationService.showMessageByCodeAndGetFeedbackAsList(messageCode);

        verify(applicationProperties, times(1)).getLocale();
        verify(messageSource, times(1)).getMessage(eq(messageCode), any(), any());
        verify(ioService, times(1)).outputString(any());
        verify(ioService, times(1)).readString();
        var expectedStingList = List.of("sting1", "string2");
        assertThat(actualStringList)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(expectedStingList);
    }
}
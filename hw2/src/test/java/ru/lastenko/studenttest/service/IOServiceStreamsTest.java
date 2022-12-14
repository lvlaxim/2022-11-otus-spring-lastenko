package ru.lastenko.studenttest.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayInputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@DisplayName("Сервис для работы с потоками ввода/вывода")
class IOServiceStreamsTest {

    @DisplayName("должен прочитать строку и разделить ее по запятым на элементы списка")
    @ParameterizedTest(name = "входная строка: \"{0}\"")
    @ValueSource(strings = {"sting1,string2", "sting1 , string2", "sting1   ,   string2"})
    void shouldReadAndSplitStringByCommas(String string) {
        var output = mock(PrintStream.class);
        var inputStream = new ByteArrayInputStream(string.getBytes());
        var input = new Scanner(inputStream);
        var ioServiceStreams = new IOServiceStreams(output, input);

        var actualStringList = ioServiceStreams.readAndSplitStringByCommasWithPrompt("prompt");

        var expectedStingList = List.of("sting1", "string2");
        assertThat(actualStringList)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(expectedStingList);
    }
}
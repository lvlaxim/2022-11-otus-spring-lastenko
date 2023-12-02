package ru.lastenko.library.formater;

import org.springframework.format.Formatter;

import java.util.Arrays;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

public abstract class AbstractFormatter<T> implements Formatter<T> {

    Map<String, String> getDataOf(Class<T> clazz, String source) {
        String className = clazz.getSimpleName();
        if (!source.startsWith(className)) {
            throw new IllegalArgumentException();
        }
        String data = source
                .replaceFirst(className + "\\(", "")
                .replaceFirst("\\)", "");
        return Arrays.stream(data.split(","))
                .map(String::trim)
                .map(d -> d.split("="))
                .collect(toMap(
                        array -> array[0],
                        array -> array[1]));
    }

}

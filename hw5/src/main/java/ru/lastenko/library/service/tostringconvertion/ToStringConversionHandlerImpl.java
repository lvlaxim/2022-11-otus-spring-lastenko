package ru.lastenko.library.service.tostringconvertion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

//TODO: разобраться с дженериками
@Component
public class ToStringConversionHandlerImpl implements ToStringConversionHandler {

    Map<Class, ToStringConverter> toStringConverterMap;

    @Autowired
    public ToStringConversionHandlerImpl(List<ToStringConverter> toStringConverters) {
        this.toStringConverterMap = toStringConverters.stream()
                .collect(Collectors.toMap(ToStringConverter::getConvertedClass, it -> it));
    }

    public String convertToString(Object object) {
        Class<?> clazz = object.getClass();
        ToStringConverter toStringConverter = getToStringConverterFor(clazz);
        if (nonNull(toStringConverter)) {
            return toStringConverter.convert(object);
        } else {
            return object.toString();
        }
    }

    public String convertToString(Collection<?> objects) {
        List<String> convertedObjects = objects.stream()
                .map(o -> "\t" + convertToString(o))
                .collect(Collectors.toList());
        return String.join(System.lineSeparator(), convertedObjects);
    }

    private ToStringConverter getToStringConverterFor(Class clazz) {
        return toStringConverterMap.get(clazz);
    }
}

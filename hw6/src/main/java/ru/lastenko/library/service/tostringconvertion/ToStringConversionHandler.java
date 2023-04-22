package ru.lastenko.library.service.tostringconvertion;

import java.util.Collection;

public interface ToStringConversionHandler {

    String convertToString(Object o);

    String convertToString(Collection<?> objects);

    String convertToStringWithSelection(Collection<?> objects, Object selectedObject);
}

package ru.lastenko.mongolibrary.service.tostringconvertion;

import java.util.Collection;

public interface ToStringConversionHandler {

    String convertToString(Object o);

    String convertToString(Collection<?> objects);

}
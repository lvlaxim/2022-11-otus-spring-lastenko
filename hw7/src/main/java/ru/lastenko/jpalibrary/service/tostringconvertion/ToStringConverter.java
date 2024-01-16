package ru.lastenko.jpalibrary.service.tostringconvertion;

public interface ToStringConverter<T> {

    Class<T> getConvertedClass();

    String convert(T domain);

}
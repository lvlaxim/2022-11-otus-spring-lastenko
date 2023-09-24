package ru.lastenko.library.service.tostringconvertion;

public interface ToStringConverter<T> {

    Class<T> getConvertedClass();

    String convert(T domain);

}
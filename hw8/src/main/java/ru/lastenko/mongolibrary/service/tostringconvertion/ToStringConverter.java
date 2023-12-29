package ru.lastenko.mongolibrary.service.tostringconvertion;

public interface ToStringConverter<T> {

    Class<T> getConvertedClass();

    String convert(T domain);

}
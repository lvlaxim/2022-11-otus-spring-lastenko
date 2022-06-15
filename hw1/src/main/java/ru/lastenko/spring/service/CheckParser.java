package ru.lastenko.spring.service;

import ru.lastenko.spring.model.Check;

import java.util.Collection;
import java.util.List;

public interface CheckParser<T> {

    Check parseCheckFrom(T  rawCheck);

    List<Check> parseChecksFrom(Collection<T> rawChecks);

}

package ru.lastenko.spring.service;

import ru.lastenko.spring.model.Check;

import java.util.Collection;

public interface CheckService {

    Collection<Check> getAll();
}

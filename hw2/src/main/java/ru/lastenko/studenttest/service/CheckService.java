package ru.lastenko.studenttest.service;

import ru.lastenko.studenttest.model.Check;

import java.util.List;

public interface CheckService {

    List<Check> getAll();

    void showChecks(List<Check> checks);

    void showCheck(Check check);
}

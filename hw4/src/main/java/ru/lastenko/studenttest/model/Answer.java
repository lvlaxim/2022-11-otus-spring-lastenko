package ru.lastenko.studenttest.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class Answer {

    private final List<String> parts;
}

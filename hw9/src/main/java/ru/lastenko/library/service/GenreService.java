package ru.lastenko.library.service;

import ru.lastenko.library.dto.GenreDto;

import java.util.List;

public interface GenreService {

    List<GenreDto> getAll();
}
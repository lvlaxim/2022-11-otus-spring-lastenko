package ru.lastenko.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lastenko.library.dao.GenreDao;
import ru.lastenko.library.domain.Genre;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreDao genreDao;
    @Override
    public List<Genre> getAll() {
        return genreDao.getAll();
    }
}

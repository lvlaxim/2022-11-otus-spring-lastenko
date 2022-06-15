package ru.lastenko.spring.service;

import ru.lastenko.spring.dao.CheckDao;
import ru.lastenko.spring.model.Check;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

@RequiredArgsConstructor
public class CheckServiceImpl implements CheckService {

    private final CheckDao checkDao;

    @Override
    public Collection<Check> getAll() {
        return checkDao.getAll();
    }
}

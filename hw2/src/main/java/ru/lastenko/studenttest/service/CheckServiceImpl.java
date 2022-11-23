package ru.lastenko.studenttest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lastenko.studenttest.dao.CheckDao;
import ru.lastenko.studenttest.exceptions.CheckLoadingException;
import ru.lastenko.studenttest.model.Check;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CheckServiceImpl implements CheckService {

    private final CheckDao checkDao;
    private final IOService ioService;

    @Override
    public List<Check> getAll() {
        try {
            return checkDao.getAll();
        } catch (CheckLoadingException e) {
            ioService.outputString("Failed to load checks.");
            return Collections.emptyList();
        }
    }
}

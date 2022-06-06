package service;

import dao.CheckDao;
import model.Check;
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

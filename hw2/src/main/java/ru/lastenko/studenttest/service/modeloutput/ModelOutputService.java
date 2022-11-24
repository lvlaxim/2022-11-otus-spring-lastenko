package ru.lastenko.studenttest.service.modeloutput;

import java.util.Collection;

public interface ModelOutputService<T> {

    void show(T model);

    default void show(Collection<T> models) {
        models.forEach(this::show);
    }

}

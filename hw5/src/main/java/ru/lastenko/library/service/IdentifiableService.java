package ru.lastenko.library.service;

import ru.lastenko.library.domain.Identifiable;

import java.util.Collection;

public interface IdentifiableService {

    <T extends Identifiable> T selectByIdFrom(Collection<T> entities);
}

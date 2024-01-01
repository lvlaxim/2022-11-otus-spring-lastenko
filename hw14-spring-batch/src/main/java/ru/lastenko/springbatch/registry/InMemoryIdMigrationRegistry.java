package ru.lastenko.springbatch.registry;

import org.bson.types.ObjectId;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import static java.util.Objects.isNull;

public class InMemoryIdMigrationRegistry implements IdMigrationRegistry {

    private final Map<Long, ObjectId> idMap = new HashMap<>();

    @Override
    public String registerAndGetNewIdBy(long oldId) {
        ObjectId newId = ObjectId.get();
        idMap.put(oldId, newId);
        return newId.toHexString();
    }

    @Override
    public String findNewIdBy(long oldId) {
        ObjectId newId = idMap.get(oldId);
        if (isNull(newId)) {
            throw new NoSuchElementException(String.format("The specified Id = '%s' is not registered.", oldId));
        }
        return newId.toHexString();
    }
}

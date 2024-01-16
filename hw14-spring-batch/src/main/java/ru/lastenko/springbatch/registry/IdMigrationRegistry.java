package ru.lastenko.springbatch.registry;

public interface IdMigrationRegistry {

    String registerAndGetNewIdBy(long oldId);

    String findNewIdBy(long oldId);
}

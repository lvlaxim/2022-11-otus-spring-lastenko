package ru.lastenko.springbatch.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import ru.lastenko.jpalibrary.model.Genre;
import ru.lastenko.springbatch.domain.MongoGenre;
import ru.lastenko.springbatch.registry.IdMigrationRegistry;

@Mapper(componentModel = "spring")
public abstract class GenreMapper {

    @Autowired
    private IdMigrationRegistry genreIdMigrationRegistry;

    @Mapping(target = "id", ignore = true)
    public abstract MongoGenre map(Genre jpaGenre);

    @AfterMapping
    protected void registerAndSetAuthorId(@MappingTarget MongoGenre mongoGenre, Genre jpaGenre) {
        long oldId = jpaGenre.getId();
        String newId = genreIdMigrationRegistry.registerAndGetNewIdBy(oldId);
        mongoGenre.setId(newId);
    }
}

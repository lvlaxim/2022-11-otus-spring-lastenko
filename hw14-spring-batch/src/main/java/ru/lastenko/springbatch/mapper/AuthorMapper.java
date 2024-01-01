package ru.lastenko.springbatch.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import ru.lastenko.jpalibrary.model.Author;
import ru.lastenko.springbatch.domain.MongoAuthor;
import ru.lastenko.springbatch.registry.IdMigrationRegistry;

@Mapper(componentModel = "spring")
public abstract class AuthorMapper {

    @Autowired
    private IdMigrationRegistry authorIdMigrationRegistry;

    @Mapping(target = "id", ignore = true)
    public abstract MongoAuthor map(Author jpaAuthor);

    @AfterMapping
    protected void registerAndSetAuthorId(@MappingTarget MongoAuthor mongoAuthor, Author jpaAuthor) {
        long oldId = jpaAuthor.getId();
        String newId = authorIdMigrationRegistry.registerAndGetNewIdBy(oldId);
        mongoAuthor.setId(newId);
    }
}

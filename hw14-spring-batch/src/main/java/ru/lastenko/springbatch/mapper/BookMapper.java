package ru.lastenko.springbatch.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import ru.lastenko.jpalibrary.model.Book;
import ru.lastenko.springbatch.domain.MongoBook;
import ru.lastenko.springbatch.registry.IdMigrationRegistry;

@Mapper(componentModel = "spring")
public abstract class BookMapper {

    @Autowired
    private IdMigrationRegistry bookIdMigrationRegistry;

    @Autowired
    private IdMigrationRegistry authorIdMigrationRegistry;

    @Autowired
    private IdMigrationRegistry genreIdMigrationRegistry;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "author.id", ignore = true)
    @Mapping(target = "genre.id", ignore = true)
    public abstract MongoBook map(Book book);

    @AfterMapping
    protected void setEntityIds(@MappingTarget MongoBook mongoBook, Book jpaBook) {
        registerAndSetBookId(mongoBook, jpaBook);
        findAndSetAuthorId(mongoBook, jpaBook);
        findAndSetGenreId(mongoBook, jpaBook);
    }

    private void registerAndSetBookId(MongoBook mongoBook, Book jpaBook) {
        long oldId = jpaBook.getId();
        String newId = bookIdMigrationRegistry.registerAndGetNewIdBy(oldId);
        mongoBook.setId(newId);
    }

    private void findAndSetAuthorId(MongoBook mongoBook, Book jpaBook) {
        long oldId = jpaBook.getAuthor().getId();
        String newIdBy = authorIdMigrationRegistry.findNewIdBy(oldId);
        mongoBook.getAuthor()
                .setId(newIdBy);
    }

    private void findAndSetGenreId(MongoBook mongoBook, Book jpaBook) {
        long oldId = jpaBook.getGenre().getId();
        String newId = genreIdMigrationRegistry.findNewIdBy(oldId);
        mongoBook.getGenre()
                .setId(newId);
    }
}

package ru.lastenko.springbatch.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import ru.lastenko.jpalibrary.model.Comment;
import ru.lastenko.springbatch.domain.MongoComment;
import ru.lastenko.springbatch.registry.IdMigrationRegistry;

@Mapper(componentModel = "spring")
public abstract class CommentMapper {

    @Autowired
    private IdMigrationRegistry bookIdMigrationRegistry;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "book.id", ignore = true)
    public abstract MongoComment map(Comment jpaComment);

    @AfterMapping
    protected void findAndSetBookId(@MappingTarget MongoComment mongoComment, Comment jpaComment) {
        long oldId = jpaComment.getBook().getId();
        String newIdBy = bookIdMigrationRegistry.findNewIdBy(oldId);
        mongoComment.getBook()
                .setId(newIdBy);
    }
}

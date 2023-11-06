package ru.lastenko.library.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.lastenko.library.model.Book;
import ru.lastenko.library.model.Comment;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {

    List<Comment> findAllByBook(Book book);

    void deleteAllByBook(Book book);
}
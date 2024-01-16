package ru.lastenko.mongolibrary.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.lastenko.mongolibrary.model.Book;
import ru.lastenko.mongolibrary.model.Comment;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {

    List<Comment> findAllByBook(Book book);

    void deleteAllByBook(Book book);
}
package ru.lastenko.library.repository;

import ru.lastenko.library.model.Book;
import ru.lastenko.library.model.Comment;

import java.util.List;

public interface CommentRepository {

    List<Comment> getAllFor(Book book);

    Comment insert(Comment comment);

    Comment getBy(long id);

    Comment update(Comment comment);

    void delete(Comment comment);

}
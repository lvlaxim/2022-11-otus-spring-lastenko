package ru.lastenko.library.service;

import ru.lastenko.library.model.Book;
import ru.lastenko.library.model.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> getAllFor(Book book);

    Comment insert(Comment comment);

    Comment getBy(long id);

    Comment update(Comment comment);

    void delete(Comment comment);
}
package ru.lastenko.jpalibrary.service;

import ru.lastenko.jpalibrary.model.Book;
import ru.lastenko.jpalibrary.model.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> getAllFor(Book book);

    Comment save(Comment comment);

    Comment getBy(long id);

    void delete(Comment comment);
}
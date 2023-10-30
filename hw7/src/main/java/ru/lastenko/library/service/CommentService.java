package ru.lastenko.library.service;

import ru.lastenko.library.model.Book;
import ru.lastenko.library.model.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> getAllFor(Book book);

    Comment save(Comment comment);

    Comment getBy(long id);

    void delete(Comment comment);
}
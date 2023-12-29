package ru.lastenko.mongolibrary.service;

import ru.lastenko.mongolibrary.model.Book;
import ru.lastenko.mongolibrary.model.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> getAllFor(Book book);

    Comment save(Comment comment);

    Comment getBy(String id);

    void delete(Comment comment);
}
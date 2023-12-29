package ru.lastenko.jpalibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lastenko.jpalibrary.model.Book;
import ru.lastenko.jpalibrary.model.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByBook(Book book);
}
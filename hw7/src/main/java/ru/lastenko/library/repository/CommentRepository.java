package ru.lastenko.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lastenko.library.model.Book;
import ru.lastenko.library.model.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByBook(Book book);
}
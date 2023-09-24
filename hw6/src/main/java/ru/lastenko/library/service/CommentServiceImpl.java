package ru.lastenko.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lastenko.library.model.Book;
import ru.lastenko.library.model.Comment;
import ru.lastenko.library.repository.CommentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final IOService ioService;

    @Override
    public List<Comment> getAllFor(Book book) {
        return commentRepository.getAllFor(book);
    }

    @Override
    @Transactional
    public Comment insert(Comment comment) {
        return commentRepository.insert(comment);
    }

    @Override
    public Comment getBy(long id) {
        Comment comment = null;
        try {
            comment = commentRepository.getBy(id);
        } catch (IllegalArgumentException e) {
            ioService.outputString("Введен несущестующий id!");
        }
        return comment;
    }

    @Override
    @Transactional
    public Comment update(Comment comment) {
        return commentRepository.update(comment);
    }

    @Override
    @Transactional
    public void delete(Comment comment) {
        commentRepository.delete(comment);
    }
}